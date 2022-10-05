package hu.nye.progtech.foxandhounds.service;

import hu.nye.progtech.foxandhounds.model.MapVO;

public class MoveValidator {

    public void outOfMap(String moveTo) {
        //System.out.println("x -> " +moveTo);
        int moveToRow = Character.getNumericValue(moveTo.charAt(0));
        int moveToColumn = Character.getNumericValue(moveTo.charAt(1));
        //System.out.println(moveToRow + " - " + moveToColumn);
        if (moveToRow < 0 || moveToRow > 7 || moveToColumn < 0 || moveToColumn > 7) {
            throw new RuntimeException("Out of Map");
        }
    }

    public void invalidMove(String moveTo, String foxLocation) {
        int moveToRow = Character.getNumericValue(moveTo.charAt(0));
        int moveToColumn = Character.getNumericValue(moveTo.charAt(1));
        int foxRow = Character.getNumericValue(foxLocation.charAt(0));
        int foxColumn = Character.getNumericValue(foxLocation.charAt(1));
        System.out.println("moveTo: " + moveTo);
        System.out.println("foxLocation: " + foxLocation);
        System.out.println("moveToRow : " + moveToRow);
        System.out.println("moveToColumn: " + moveToColumn);
        System.out.println("foxRow: " + foxRow);
        System.out.println("foxColumn: " + foxColumn);
        if (!((moveToRow == foxRow - 1 && moveToColumn == foxColumn - 1) ||
                (moveToRow == foxRow - 1 && moveToColumn == foxColumn + 1) ||
                (moveToRow == foxRow + 1 && moveToColumn == foxColumn - 1) ||
                (moveToRow == foxRow + 1 && moveToColumn == foxColumn + 1))) {
            throw new RuntimeException("Invalid move");
        }
    }

    public void occupiedSpace(MapVO mapVO, String moveTo, String foxLocation) {
        int moveToRow = Character.getNumericValue(moveTo.charAt(0));
        int moveToColumn = Character.getNumericValue(moveTo.charAt(1));
        int foxRow = Character.getNumericValue(foxLocation.charAt(0));
        int foxColumn = Character.getNumericValue(foxLocation.charAt(1));
        if (mapVO.getMap()[moveToRow][moveToColumn] == 'H') {
            throw new RuntimeException("Space is occupied");
        }
    }
}