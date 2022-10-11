package hu.nye.progtech.foxandhounds.service.exception;

/**
 * Exception to represent that a move was invalid.
 */
public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(String message) {
        super(message);
    }
}
