package api_gestao_estacionamento.projeto.service.mail.templates;


public interface EmailTemplate {

    String getSubject();
    String getText();
    void setUserToken(String token);

    default String getFirstName(String fullname){
        fullname = fullname.trim();
        String[] names = fullname.split(" ");
        return names[0];
    }


}
