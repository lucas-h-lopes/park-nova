package api_gestao_estacionamento.projeto.jwt;

import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.loadUserByUsername(username, true);
        return new JwtUserDetails(user);
    }

    public JwtToken generateToken(String username){
        User.Role role = userService.findRoleFromUsername(username);
        return jwtUtils.generateToken(username, role.name());
    }
}
