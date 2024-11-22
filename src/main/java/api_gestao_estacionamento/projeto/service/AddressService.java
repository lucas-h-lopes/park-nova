package api_gestao_estacionamento.projeto.service;

import api_gestao_estacionamento.projeto.model.Address;
import api_gestao_estacionamento.projeto.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Address insert(Address address) {
        return addressRepository.save(address);
    }
}
