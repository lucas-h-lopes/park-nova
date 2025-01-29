package api_gestao_estacionamento.projeto.service.exception;

public class UserIsAlreadyActiveException extends RuntimeException{
    public UserIsAlreadyActiveException(String msg){
        super(msg);
    }
}
