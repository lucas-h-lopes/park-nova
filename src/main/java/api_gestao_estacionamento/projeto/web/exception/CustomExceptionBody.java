package api_gestao_estacionamento.projeto.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.TreeMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomExceptionBody {

    private String path;
    private String method;
    private Integer status;
    private String statusMessage;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    public CustomExceptionBody(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusMessage = status.getReasonPhrase();
        this.message = message;
    }

    public CustomExceptionBody(HttpServletRequest request, HttpStatus status, String message, BindingResult result) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusMessage = status.getReasonPhrase();
        this.message = message;
        this.errors = new TreeMap<>();
        addErrors(result);
    }

    private void addErrors(BindingResult result) {
        for (FieldError error : result.getFieldErrors()) {
            this.errors.put(error.getField(), error.getDefaultMessage());
        }
    }
}
