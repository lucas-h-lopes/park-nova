package api_gestao_estacionamento.projeto.service.exception;

public class InactiveAccountException extends RuntimeException{
    public InactiveAccountException(String msg){
        super(msg);
    }
}
