package pagination.exception;

public class InvalidPageSizeException extends RuntimeException {

    public InvalidPageSizeException(String message) {
        super(message);
    }
}
