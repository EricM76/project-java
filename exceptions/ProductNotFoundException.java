package exceptions;

/**
 * Excepción que maneja cuando el producto no se encuentra
 * @author Eric
 * @version 1.0.0
 */
public class ProductNotFoundException extends ProductException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
