package api_gestao_estacionamento.projeto.repository.projection;

public interface AddressProjection {
    String getStreet();
    String getNumber();
    String getCity();
    String getState();
    String getZipCode();
    String getCountry();
}
