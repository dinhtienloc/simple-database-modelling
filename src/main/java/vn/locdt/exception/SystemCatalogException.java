package vn.locdt.exception;

/**
 * Created by locdt on 2/2/2018.
 */
public class SystemCatalogException extends RuntimeException {
    public SystemCatalogException(String message) {
        super(message);
    }

    public SystemCatalogException(String message, Throwable cause) {
        super(message, cause);
    }
}
