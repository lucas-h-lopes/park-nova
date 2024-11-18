package api_gestao_estacionamento.projeto.service.exception;

public class InvalidActivationTokenException extends RuntimeException{
    public InvalidActivationTokenException(String error){
        super(error);
    }
}
