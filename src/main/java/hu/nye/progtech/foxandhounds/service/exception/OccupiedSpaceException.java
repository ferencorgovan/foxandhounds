package hu.nye.progtech.foxandhounds.service.exception;

/**
 * Exception to represent that a map space was occupied.
 */
public class OccupiedSpaceException extends RuntimeException {
    public OccupiedSpaceException(String message) {
        super(message);
    }
}
