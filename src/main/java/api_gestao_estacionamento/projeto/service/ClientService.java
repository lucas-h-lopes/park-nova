package api_gestao_estacionamento.projeto.service;

import api_gestao_estacionamento.projeto.jwt.JwtUserDetails;
import api_gestao_estacionamento.projeto.model.Address;
import api_gestao_estacionamento.projeto.model.Client;
import api_gestao_estacionamento.projeto.repository.ClientRepository;
import api_gestao_estacionamento.projeto.repository.projection.ClientProjection;
import api_gestao_estacionamento.projeto.service.exception.CpfUniqueViolationException;
import api_gestao_estacionamento.projeto.service.exception.EntityNotFoundException;
import api_gestao_estacionamento.projeto.service.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final AddressService addressService;
    private final ClientRepository clientRepository;

    @Transactional
    public Client insert(Client client) {
        Address address = client.getAddress();
        addressService.insert(address);
        try {
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException e) {
            throw new CpfUniqueViolationException(String.format("CPF '%s' já cadastrado no sistema", client.getCpf()));
        }
    }

    @Transactional(readOnly = true)
    public Page<ClientProjection> findAll(Pageable pageable) {
        return clientRepository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public Client getById(Long id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Cliente com id '%s' não foi encontrado no sistema", id))
        );
    }

    @Transactional(readOnly = true)
    public Client getByUserId(Long id) {
        return clientRepository.findByUserIdOptional(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("O usuário com id '%d' não possui registro de cliente.", id))
        );
    }

    @Transactional(readOnly = true)
    public Client getByUserId(Long id, JwtUserDetails details) {
        Client client = clientRepository.findByClientId(id);

        switch (details.getRole()) {
            case "ROLE_ADMIN" -> {
                if (client == null) {
                    throw new EntityNotFoundException(String.format("Cliente com id '%s' não foi encontrado no sistema", id));
                }
                return client;
            }
            default -> {
                if (client == null || client.getUser().getId() != details.getId()) {
                    throw new ForbiddenException("O usuário autenticado não possui permissão para visualizar o recurso");
                }
                return client;
            }
        }
    }

    @Transactional
    public void deleteById(Long id, JwtUserDetails details) {
        Client client = getByUserId(id, details);
        clientRepository.deleteById(id);
        addressService.deleteById(client.getAddress().getId());
    }

    @Transactional
    public void updateAddress(String street, String number, String zipCode, String city, String state, String country, JwtUserDetails details) {
        Client client = getByUserId(details.getId());
        Address address = client.getAddress();

        address.setStreet(street);
        address.setNumber(number);
        address.setZipCode(zipCode);
        address.setCity(city);
        address.setState(state);
        address.setCountry(country);

        client.setAddress(address);
    }
}
