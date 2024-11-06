package api_gestao_estacionamento.projeto.jwt;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;


public class JwtUserDetails extends User {

    private api_gestao_estacionamento.projeto.model.User user;

    public JwtUserDetails(api_gestao_estacionamento.projeto.model.User user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }

    public Long getId(){
        return user.getId();
    }

    public String getRole(){
        return user.getRole().name();
    }
}
