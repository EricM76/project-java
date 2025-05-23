package exceptions;

/**
 * Excepción que maneja cuando se ingresa valores inválidos
 * @author Eric
 * @version 1.0.0
 */
public class InvalidValueException extends ProductException {
    public InvalidValueException(String message) {
        super(message);
    }
}
