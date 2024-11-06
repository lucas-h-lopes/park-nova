package api_gestao_estacionamento.projeto.service.exception;

public class UsernameUniqueViolationException extends RuntimeException{
    public UsernameUniqueViolationException(String message){
        super(message);
    }
}
