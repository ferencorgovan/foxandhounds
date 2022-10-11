package hu.nye.progtech.foxandhounds.service;

import java.util.regex.Pattern;

import hu.nye.progtech.foxandhounds.model.MapVO;

public class MoveValidator {
    private static final String COORDINATE_REGEX = "^[0-7][0-7]$";

    public Boolean isValidCoordinate(String coordinate) {
        return Pattern.matches(COORDINATE_REGEX, coordinate);
    }

    public Boolean isValidMove(String moveTo, String foxLocation) {
        int moveToRow = Character.getNumericValue(moveTo.charAt(0));
        int moveToColumn = Character.getNumericValue(moveTo.charAt(1));
        int foxRow = Character.getNumericValue(foxLocation.charAt(0));
        int foxColumn = Character.getNumericValue(foxLocation.charAt(1));
        return ((moveToRow == foxRow + 1 || moveToRow == foxRow - 1) &&
                (moveToColumn == foxColumn + 1 || moveToColumn == foxColumn - 1));
    }

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