package hu.nye.progtech.foxandhounds.service;

import hu.nye.progtech.foxandhounds.model.MapVO;

public class MoveValidator {

    public void outOfMap(String moveTo) {
        int moveToRow = Character.getNumericValue(moveTo.charAt(0));
        int moveToColumn = Character.getNumericValue(moveTo.charAt(1));
        if (moveToRow < 0 || moveToRow > 7 || moveToColumn < 0 || moveToColumn > 7) {
            throw new RuntimeException("Invalid move - Out of Map");
        }
    }

    public void invalidMove(String moveTo, String foxLocation) {
        int moveToRow = Character.getNumericValue(moveTo.charAt(0));
        int moveToColumn = Character.getNumericValue(moveTo.charAt(1));
        int foxRow = Character.getNumericValue(foxLocation.charAt(0));
        int foxColumn = Character.getNumericValue(foxLocation.charAt(1));
        if (!((moveToRow == foxRow - 1 && moveToColumn == foxColumn - 1) ||
                (moveToRow == foxRow - 1 && moveToColumn == foxColumn + 1) ||
                (moveToRow == foxRow + 1 && moveToColumn == foxColumn - 1) ||
                (moveToRow == foxRow + 1 && moveToColumn == foxColumn + 1)) ||
                !(moveToRow >= 0 && moveToRow < 7 && moveToColumn >=0 && moveToColumn <= 7)) {
            throw new RuntimeException("Invalid move");
        }
    }

    public void occupiedSpace(MapVO mapVO, String moveTo, String foxLocation) {
        int moveToRow = Character.getNumericValue(moveTo.charAt(0));
        int moveToColumn = Character.getNumericValue(moveTo.charAt(1));
        int foxRow = Character.getNumericValue(foxLocation.charAt(0));
        int foxColumn = Character.getNumericValue(foxLocation.charAt(1));
        if (mapVO.getMap()[moveToRow][moveToColumn] == 'H') {
            throw new RuntimeException("Invalid move - Space is occupied");
        }
    }
}