package api_gestao_estacionamento.projeto.service.exception;

public class ActivationTokenAlreadyUsedException extends RuntimeException{
    public ActivationTokenAlreadyUsedException(String msg){
        super(msg);
    }
}
