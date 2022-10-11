package hu.nye.progtech.foxandhounds.service;

import java.util.Random;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;

public class Move {
    private final MoveValidator moveValidator = new MoveValidator();

    public void foxMove(GameState gameState, String moveTo) throws RuntimeException {
        MapVO currentMap = gameState.getCurrentMap();
        String foxLocation = gameState.getCurrentMap().getFoxStart();

        if (!moveValidator.isValidCoordinate(moveTo)) {
            throw new RuntimeException("Out of map");
        }
        moveValidator.invalidMove(moveTo, foxLocation);

        if (!moveValidator.isFree(currentMap, moveTo)) {
            throw new RuntimeException("Space is occupied");
        }

        int moveToRow = Character.getNumericValue(moveTo.charAt(0));
        int moveToColumn = Character.getNumericValue(moveTo.charAt(1));
        int foxRow = Character.getNumericValue(foxLocation.charAt(0));
        int foxColumn = Character.getNumericValue(foxLocation.charAt(1));

        System.out.println("Moving fox to: " + moveToRow + moveToColumn);
        char[][] map = gameState.getCurrentMap().getMap();
        map[foxRow][foxColumn] = '*';
        map[moveToRow][moveToColumn] = 'F';
        String[] houndsStart = gameState.getCurrentMap().getHoundsStart();
        if (moveToRow == 0) {
            gameState.setGameOver(true);
        }
        gameState.setCurrentMap(new MapVO(currentMap.getMapLength(), map, moveTo, houndsStart));
    }

    public void enemyMove(GameState gameState) {
        MapVO currentMap = gameState.getCurrentMap();
        String[] hounds = gameState.getCurrentMap().getHoundsStart();
        char[][] map = currentMap.getMap();
        Random r = new Random();

        int selectedHound = -1;
        int houndRowIndex = -1;
        int houndColumnIndex = -1;
        String houndRow = "";
        String houndColumn = "";

        while (!moveValidator.isValidCoordinate(houndRow + houndColumn) ||
                !moveValidator.isFree(currentMap, houndRow + houndColumn)) {
            selectedHound = r.nextInt(4);
            //System.out.println("Selected Hound #" + selectedHound);
            houndRowIndex = Character.getNumericValue(hounds[selectedHound].charAt(0));
            houndColumnIndex = Character.getNumericValue(hounds[selectedHound].charAt(1));
            houndRow = String.valueOf(houndRowIndex + 1);
            houndColumn = String.valueOf(houndColumnIndex + (r.nextBoolean() ? 1 : -1));
        }
        // TODO: enemy move - index 8 out of bounds fo length 8

        //System.out.println("Moving Hound #" + selectedHound + " to " + houndRow + houndColumn);
        map[houndRowIndex][houndColumnIndex] = '*';
        map[Integer.parseInt(houndRow)][Integer.parseInt(houndColumn)] = 'H';

        hounds[selectedHound] = houndRow + houndColumn;
        String foxLocation = gameState.getCurrentMap().getFoxStart();

        int foxRow = Character.getNumericValue(foxLocation.charAt(0));
        int foxColumn = Character.getNumericValue(foxLocation.charAt(1));
        /*if (map[foxRow-1][foxColumn-1] == 'H' && map[foxRow-1][foxColumn+1] == 'H' &&
        map[foxRow+1][foxColumn-1] == 'H' && map[foxRow+1][foxColumn+1] == 'H') {
            gameState.setGameOver(true);
            System.out.println("Hounds win.");
        }*/
        gameState.setCurrentMap(new MapVO(currentMap.getMapLength(), map, foxLocation, hounds));
    }
}
