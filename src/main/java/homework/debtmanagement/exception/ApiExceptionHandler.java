package homework.debtmanagement.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            CustomerNotFoundException.class,
            DebtNotFoundException.class
    })
    public ResponseEntity<Object> handleNotFound(RuntimeException ex) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), NOT_FOUND);
    }

    @ExceptionHandler({
            ConstraintViolationException.class,
            DataIntegrityViolationException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<Object> handleBadRequest(Exception ex) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(
                ex.getBindingResult().getAllErrors().stream()
                        .map(err -> err.getDefaultMessage())
                        .collect(toList()),
                BAD_REQUEST
        );
    }

}
