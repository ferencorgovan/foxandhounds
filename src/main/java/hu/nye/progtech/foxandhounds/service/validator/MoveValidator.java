package hu.nye.progtech.foxandhounds.service.validator;

import java.util.regex.Pattern;

import hu.nye.progtech.foxandhounds.model.MapVO;

/**
 * Validates components of player of enemy move actions.
 */
public class MoveValidator {
    private static final String COORDINATE_REGEX = "^[0-7][0-7]$";

    /**
     * Validates a map coordinate. It should not be out of map bounds.
     *
     * @param coordinate Coordinate of move destination
     * @return {@code true} if the coordinate is valid, {@code false} otherwise
     */
    public Boolean isValidCoordinate(String coordinate) {
        return Pattern.matches(COORDINATE_REGEX, coordinate);
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