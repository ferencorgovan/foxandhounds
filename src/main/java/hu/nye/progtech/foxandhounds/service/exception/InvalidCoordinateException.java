package hu.nye.progtech.foxandhounds.service.exception;

/**
 * Exception to represent that a coordinate was invalid.
 */
public class InvalidCoordinateException extends RuntimeException {
    public InvalidCoordinateException(String message) {
        super(message);
    }
}
