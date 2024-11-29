package api_gestao_estacionamento.projeto.service;

import api_gestao_estacionamento.projeto.model.Address;
import api_gestao_estacionamento.projeto.repository.AddressRepository;
import api_gestao_estacionamento.projeto.service.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public Address insert(Address address) {
        return addressRepository.save(address);
    }

    @Transactional
    public void deleteById(Long id) {
        getById(id);
        addressRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Address getById(Long id) {
        return addressRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Endereço com id '%d' não foi encontrado no sistema", id))
        );
    }
}
