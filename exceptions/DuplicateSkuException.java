package exceptions;

/**
 * Excepción que maneja cuando que el SKU está duplicado
 * TODO: se implementará cuando se habilite el ingreso manual
 * @author Eric
 * @version 1.0.0
 */
public class DuplicateSkuException extends ProductException {
    public DuplicateSkuException(String message) {
        super(message);
    }
}
