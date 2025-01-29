package api_gestao_estacionamento.projeto.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class EqualStringMatcher extends TypeSafeMatcher<String> {

    private final String s;

    public EqualStringMatcher(String s) {
        this.s = s;
    }

    @Override
    protected boolean matchesSafely(String s) {
        return s.equals(this.s);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("equals to " + s);
    }
}
