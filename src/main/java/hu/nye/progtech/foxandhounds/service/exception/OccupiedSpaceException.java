package hu.nye.progtech.foxandhounds.service.exception;

public class OccupiedSpaceException extends RuntimeException {
    public OccupiedSpaceException(String message) {
        super(message);
    }
}
