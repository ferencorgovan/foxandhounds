package hu.nye.progtech.foxandhounds.service.validator;

import java.util.regex.Pattern;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.service.exception.InvalidCoordinateException;
import hu.nye.progtech.foxandhounds.service.exception.InvalidMoveException;
import hu.nye.progtech.foxandhounds.service.exception.OccupiedSpaceException;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;

/**
 * Validates components of player of enemy move actions.
 */
public class MoveValidator {
    private final GameState gameState;
    private final PrintWrapper printWrapper;

    public MoveValidator(GameState gameState, PrintWrapper printWrapper) {
        this.gameState = gameState;
        this.printWrapper = printWrapper;
    }

    /**
     * Validates a map coordinate. Throws Exception if the coordinate is invalid.
     *
     * @param coordinate Coordinate of move destination
     */
    public void validateCoordinate(String coordinate) {
        if (!isValidCoordinate(coordinate)) {
            printWrapper.printLine("Out of map");
            throw new InvalidCoordinateException("Out of map");
        }
    }

    /**
     * Validates a game move. Throws Exception if the move is invalid.
     *
     * @param coordinate Coordinate of move destination
     * @param foxLocation Coordinate of fox position
     */
    public void validateMove(String coordinate, String foxLocation) {
        if (!isValidMove(coordinate, foxLocation)) {
            printWrapper.printLine("Invalid move");
            throw new InvalidMoveException("Invalid move");
        }
    }

    /**
     * Determines if the map space is free. Throws Exception is the space is occupied.
     *
     * @param currentMap Current game map
     * @param coordinate Coordinate of move destination
     */
    public void validateFreeSpace(MapVO currentMap, String coordinate) {
        if (!isFree(currentMap, coordinate)) {
            printWrapper.printLine("Space is occupied");
            throw new OccupiedSpaceException("Space is occupied");
        }
    }

    /**
     * Validates a map coordinate. It should not be out of map bounds.
     *
     * @param coordinate Coordinate of move destination
     * @return {@code true} if the coordinate is valid, {@code false} otherwise
     */
    public Boolean isValidCoordinate(String coordinate) {
        int mapBound = gameState.getCurrentMap().getMapLength() - 1;
        String coordinateRegex = "^[0-" + mapBound + "][0-" + mapBound + "]$";
        return Pattern.matches(coordinateRegex, coordinate);
    }

    /**
     * Validates a game move. It should be one space apart diagonally.
     *
     * @param coordinate Coordinate of move destination
     * @param foxLocation Coordinate of fox position
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    public Boolean isValidMove(String coordinate, String foxLocation) {
        int moveToRow = Character.getNumericValue(coordinate.charAt(0));
        int moveToColumn = Character.getNumericValue(coordinate.charAt(1));
        int foxRow = Character.getNumericValue(foxLocation.charAt(0));
        int foxColumn = Character.getNumericValue(foxLocation.charAt(1));
        return ((moveToRow == foxRow + 1 || moveToRow == foxRow - 1) &&
                (moveToColumn == foxColumn + 1 || moveToColumn == foxColumn - 1));
    }

    /**
     * Determines if the map space is free.
     *
     * @param mapVO Current game map
     * @param coordinate Coordinate of move destination
     * @return {@code true} if the map space is free, {@code false} otherwise
     */
    public Boolean isFree(MapVO mapVO, String coordinate) {
        boolean result = true;
        int row = Character.getNumericValue(coordinate.charAt(0));
        int column = Character.getNumericValue(coordinate.charAt(1));
        if (mapVO.getMap()[row][column] != '*') {
            result = false;
        }
        return result;
    }
}