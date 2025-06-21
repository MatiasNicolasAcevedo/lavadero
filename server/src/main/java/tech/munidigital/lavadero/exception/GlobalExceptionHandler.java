package tech.munidigital.lavadero.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para los controladores REST.
 * <p>
 * Captura y formatea de forma uniforme los errores de validación
 * y las {@link ResponseStatusException} personalizadas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Intercepta errores de validación provenientes de {@code @Valid}.
   *
   * @param ex excepción con detalle de campos inválidos.
   * @return mapa <em>field → message</em> con código 400 (Bad Request).
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  /**
   * Maneja las {@link ResponseStatusException} lanzadas manualmente en el código.
   *
   * @param ex excepción con el <i>reason</i> y el {@link HttpStatus} asociado.
   * @return estructura JSON <code>{"error": &lt;reason&gt;}</code> con el
   * código HTTP indicado por la propia excepción.
   */
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getReason());
    return new ResponseEntity<>(error, ex.getStatusCode());
  }

}