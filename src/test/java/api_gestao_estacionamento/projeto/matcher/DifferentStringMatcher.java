package api_gestao_estacionamento.projeto.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DifferentStringMatcher extends TypeSafeMatcher<String> {

    private final String expected;

    public DifferentStringMatcher(String expected) {
        if(expected == null){
            throw new IllegalArgumentException("The expected value should not be `null`");
        }
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(String actual) {
        return actual != null && !actual.equals(expected);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("different to " + expected);
    }
}
