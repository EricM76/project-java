package exceptions;

/**
 * Excepción que maneja errores generales relacionados con productos
 * @author Eric
 * @version 1.0.0
 */
public class ProductException extends Exception {
    public ProductException(String message) {
        super(message);
    }
}

