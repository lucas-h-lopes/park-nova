package api_gestao_estacionamento.projeto.service.exception;

public class CpfUniqueViolationException extends RuntimeException{
    public CpfUniqueViolationException(String msg){
        super(msg);
    }
}
