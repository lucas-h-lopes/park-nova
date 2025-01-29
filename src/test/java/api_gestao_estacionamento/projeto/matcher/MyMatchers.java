package api_gestao_estacionamento.projeto.matcher;

public class MyMatchers {

    private MyMatchers(){}

    public static EqualStringMatcher equalString(String s){
        return new EqualStringMatcher(s);
    }

    public static DifferentStringMatcher differentString(String s){
        return new DifferentStringMatcher(s);
    }
}
