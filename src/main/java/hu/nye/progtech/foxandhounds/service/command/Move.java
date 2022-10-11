package hu.nye.progtech.foxandhounds.service.command;

import java.util.Random;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.service.exception.InvalidCoordinateException;
import hu.nye.progtech.foxandhounds.service.exception.InvalidMoveException;
import hu.nye.progtech.foxandhounds.service.exception.OccupiedSpaceException;
import hu.nye.progtech.foxandhounds.service.validator.MoveValidator;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Move {
    private final MoveValidator moveValidator;
    private final PrintWrapper printWrapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(Move.class);

    public Move(MoveValidator moveValidator, PrintWrapper printWrapper) {
        this.moveValidator = moveValidator;
        this.printWrapper = printWrapper;
    }

    public void foxMove(GameState gameState, String moveTo) throws RuntimeException {
        LOGGER.info("Performing player move");
        MapVO currentMap = gameState.getCurrentMap();
        String foxLocation = gameState.getCurrentMap().getFoxStart();

        if (!moveValidator.isValidCoordinate(moveTo)) {
            printWrapper.printLine("Out of map");
            throw new InvalidCoordinateException("Out of map");
        }
        if (!moveValidator.isValidMove(moveTo, foxLocation)) {
            printWrapper.printLine("Invalid move");
            throw new InvalidMoveException("Invalid move");
        }

        if (!moveValidator.isFree(currentMap, moveTo)) {
            printWrapper.printLine("Space is occupied");
            throw new OccupiedSpaceException("Space is occupied");
        }

        int moveToRow = Character.getNumericValue(moveTo.charAt(0));
        int moveToColumn = Character.getNumericValue(moveTo.charAt(1));
        int foxRow = Character.getNumericValue(foxLocation.charAt(0));
        int foxColumn = Character.getNumericValue(foxLocation.charAt(1));

        printWrapper.printLine("Moving fox to: " + moveToRow + moveToColumn);
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
        LOGGER.info("Performing enemy move");

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
            houndRowIndex = Character.getNumericValue(hounds[selectedHound].charAt(0));
            houndColumnIndex = Character.getNumericValue(hounds[selectedHound].charAt(1));
            houndRow = String.valueOf(houndRowIndex + 1);
            houndColumn = String.valueOf(houndColumnIndex + (r.nextBoolean() ? 1 : -1));
        }
        printWrapper.printLine("Enemy moves Hound #" + (selectedHound + 1) + " to " + houndRow + houndColumn);
        map[houndRowIndex][houndColumnIndex] = '*';
        map[Integer.parseInt(houndRow)][Integer.parseInt(houndColumn)] = 'H';

        hounds[selectedHound] = houndRow + houndColumn;
        String foxLocation = gameState.getCurrentMap().getFoxStart();

        /*
        int foxRow = Character.getNumericValue(foxLocation.charAt(0));
        int foxColumn = Character.getNumericValue(foxLocation.charAt(1));
        if (map[foxRow-1][foxColumn-1] == 'H' && map[foxRow-1][foxColumn+1] == 'H' &&
        map[foxRow+1][foxColumn-1] == 'H' && map[foxRow+1][foxColumn+1] == 'H') {
            gameState.setGameOver(true);
            System.out.println("Hounds win.");
        }
        */
        gameState.setCurrentMap(new MapVO(currentMap.getMapLength(), map, foxLocation, hounds));
    }
}
