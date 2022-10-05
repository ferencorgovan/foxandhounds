package hu.nye.progtech.foxandhounds.service;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;

public class Move {
    private final MoveValidator moveValidator = new MoveValidator();

    public MapVO foxMove(MapVO mapVO, GameState gameState, String moveTo) throws RuntimeException {
        String foxLocation = gameState.getCurrentMap().getFoxStart();

        moveValidator.outOfMap(moveTo);
        moveValidator.invalidMove(moveTo, foxLocation);
        moveValidator.occupiedSpace(mapVO, moveTo, foxLocation);

        int moveToRow = Character.getNumericValue(moveTo.charAt(0));
        int moveToColumn = Character.getNumericValue(moveTo.charAt(1));
        int foxRow = Character.getNumericValue(foxLocation.charAt(0));
        int foxColumn = Character.getNumericValue(foxLocation.charAt(1));

        char[][] map = gameState.getCurrentMap().getMap();
        map[foxRow][foxColumn] = 'O';
        map[moveToRow][moveToColumn] = 'F';

        return new MapVO(mapVO.getMapLength(), map, moveTo);

    }
}
