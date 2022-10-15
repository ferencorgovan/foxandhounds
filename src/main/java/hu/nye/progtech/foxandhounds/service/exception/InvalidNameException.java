package hu.nye.progtech.foxandhounds.service.exception;

/**
 * Exception to represent that a player name was invalid.
 */
public class InvalidNameException extends RuntimeException {
    public InvalidNameException(String message) {
        super(message);
    }
}
