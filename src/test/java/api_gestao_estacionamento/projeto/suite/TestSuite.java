package api_gestao_estacionamento.projeto.suite;

import api_gestao_estacionamento.projeto.service.ActivationServiceTest;
import api_gestao_estacionamento.projeto.service.EmailServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ActivationServiceTest.class, EmailServiceTest.class})
public class TestSuite {

}
