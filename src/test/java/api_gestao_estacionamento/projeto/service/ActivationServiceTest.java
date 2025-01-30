package api_gestao_estacionamento.projeto.service;

import api_gestao_estacionamento.projeto.builder.UserBuilder;
import api_gestao_estacionamento.projeto.model.User;
import api_gestao_estacionamento.projeto.service.exception.InvalidActivationTokenException;
import api_gestao_estacionamento.projeto.service.exception.UserIsAlreadyActiveException;
import api_gestao_estacionamento.projeto.util.ActivationTokenUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static api_gestao_estacionamento.projeto.matcher.MyMatchers.equalString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ActivationServiceTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @InjectMocks
    public ActivationService service;

    @Mock
    public UserService userService;

    @Before
    public void setup() {
       openMocks(this);
    }

    private final Long userId = 100L;

    @Test
    public void shouldThrowExceptionWhenTokensDoNotMatch(){
            //scenario
            User user = UserBuilder.createInactiveUser().get();
            String anotherToken = ActivationTokenUtils.generateActivationToken();

            doReturn(user).when(userService).findUserById(userId);

        try {
            //action
            service.activateAccount(userId, anotherToken);
            fail("Expected exception was not thrown");
            //verify
        }catch(InvalidActivationTokenException e){
            error.checkThat(e.getMessage(), equalString("Token inválido"));
        }
    }

    @Test
    public void shouldThrowExceptionWhenUserIsActive(){
        //scenario
        User user = UserBuilder.createOldActiveUser().get();

        doReturn(user).when(userService).findUserById(userId);

        doThrow(new UserIsAlreadyActiveException("O usuário já está ativo no sistema"))
                .when(userService)
                .throwExceptionIfUserIsActive(user);

        try {
            //action
            service.activateAccount(userId, user.getActivationToken());
            fail("Expected exception was not thrown");
            //verify
        }catch(UserIsAlreadyActiveException e){
            error.checkThat(e.getMessage(), equalString("O usuário já está ativo no sistema"));
        }
    }

    @Test
    public void shouldReturnTokenExpiredMessageWhenInactiveUserLastModifiedMoreThan24HoursAgo(){
        //scenario
        User user = UserBuilder.createOldInactiveUser().get();

        doReturn(user).when(userService).findUserById(userId);

        //action
        String result = service.activateAccount(userId, user.getActivationToken());

        //verify
        error.checkThat(result, equalString("Token expirado por inatividade, solicite a um administrador um novo código."));
    }

    @Test
    public void shouldActivateUserAndReturnSuccessMessageWhenUserIsInactive(){
        //scenario
        User user = UserBuilder.createInactiveUser().get();
        boolean originalStatus = user.isActive();

        doReturn(user).when(userService).findUserById(userId);

        //action
        String result = service.activateAccount(userId, user.getActivationToken());

        //verify
        error.checkThat(result, equalString("Conta ativada com sucesso!"));
        error.checkThat(originalStatus, is(false));
        error.checkThat(user.isActive(), is(true));
    }
}
