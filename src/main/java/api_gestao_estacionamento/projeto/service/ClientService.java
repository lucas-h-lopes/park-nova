package api_gestao_estacionamento.projeto.service;

import api_gestao_estacionamento.projeto.model.Address;
import api_gestao_estacionamento.projeto.model.Client;
import api_gestao_estacionamento.projeto.repository.ClientRepository;
import api_gestao_estacionamento.projeto.service.exception.CpfUniqueViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final UserService userService;
    private final AddressService addressService;
    private final ClientRepository clientRepository;

    public Client insert(Client client) {
        Address address = client.getAddress();
        addressService.insert(address);
        try {
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException e) {
            throw new CpfUniqueViolationException(String.format("CPF '%s' já cadastrado no sistema", client.getCpf()));
        }
    }
}
