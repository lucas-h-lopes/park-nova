package api_gestao_estacionamento.projeto.service.exception;

public class PasswordInvalidException extends RuntimeException{
    public PasswordInvalidException(String msg){
        super(msg);
    }
}
