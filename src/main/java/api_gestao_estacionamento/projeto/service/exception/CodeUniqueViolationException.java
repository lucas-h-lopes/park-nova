package api_gestao_estacionamento.projeto.service.exception;

public class CodeUniqueViolationException extends RuntimeException{
    public CodeUniqueViolationException(String msg){
        super(msg);
    }
}
