package hu.nye.progtech.foxandhounds.service;

import java.util.Random;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;

public class Move {
    private final MoveValidator moveValidator = new MoveValidator();

    public MapVO foxMove(MapVO mapVO, GameState gameState, String moveTo) throws RuntimeException {
        String foxLocation = gameState.getCurrentMap().getFoxStart();
        String[] houndsStart = gameState.getCurrentMap().getHoundsStart();
        moveValidator.outOfMap(moveTo);
        moveValidator.invalidMove(moveTo, foxLocation);
        moveValidator.occupiedSpace(mapVO, moveTo, foxLocation);

        int moveToRow = Character.getNumericValue(moveTo.charAt(0));
        int moveToColumn = Character.getNumericValue(moveTo.charAt(1));
        int foxRow = Character.getNumericValue(foxLocation.charAt(0));
        int foxColumn = Character.getNumericValue(foxLocation.charAt(1));

        System.out.println("Moving fox to: " + moveToRow + moveToColumn);
        char[][] map = gameState.getCurrentMap().getMap();
        map[foxRow][foxColumn] = '*';
        map[moveToRow][moveToColumn] = 'F';

        return new MapVO(mapVO.getMapLength(), map, moveTo, houndsStart);
    }

    public MapVO enemyMove(MapVO mapVO, GameState gameState) {
        String foxLocation = gameState.getCurrentMap().getFoxStart();

        String[] hounds = gameState.getCurrentMap().getHoundsStart();

        Random r = new Random();
        int selectedHound = r.nextInt(4);

        char[][] map = gameState.getCurrentMap().getMap();

        int houndRowIndex = Character.getNumericValue(hounds[selectedHound].charAt(0));
        int houndColumnIndex = Character.getNumericValue(hounds[selectedHound].charAt(1));

        map[houndRowIndex][houndColumnIndex] = '*';

        String houndRow = String.valueOf(houndRowIndex+1);
        String houndColumn = String.valueOf(houndColumnIndex + (r.nextBoolean() ? 1: -1));

        moveValidator.outOfMap(houndRow + houndColumn);
        map[Integer.valueOf(houndRow)][Integer.valueOf(houndColumn)] = 'H';

        hounds[selectedHound] = houndRow + houndColumn;

        return new MapVO(mapVO.getMapLength(), map, foxLocation, hounds);
    }
}
