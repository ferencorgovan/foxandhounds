package hu.nye.progtech.foxandhounds;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.model.Player;
import hu.nye.progtech.foxandhounds.service.RandomGenerator;
import hu.nye.progtech.foxandhounds.service.command.CommandHandler;
import hu.nye.progtech.foxandhounds.service.command.Move;
import hu.nye.progtech.foxandhounds.service.game.GameController;
import hu.nye.progtech.foxandhounds.service.game.GameStepPerformer;
import hu.nye.progtech.foxandhounds.service.input.InputReader;
import hu.nye.progtech.foxandhounds.service.map.MapGenerator;
import hu.nye.progtech.foxandhounds.service.validator.MoveValidator;
import hu.nye.progtech.foxandhounds.ui.MapPrinter;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;

/**
 * Entry point of Fox and Hounds game.
 */
public class Main {
    /**
     * Entrypoint of the game.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        RandomGenerator randomGenerator = new RandomGenerator();
        PrintWrapper printWrapper = new PrintWrapper();
        MapGenerator mapGenerator = new MapGenerator(randomGenerator);
        MapVO mapVO = mapGenerator.generateMap(8);
        Player player = new Player("Player");
        GameState gameState = new GameState(mapVO, false, player);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        InputReader inputReader = new InputReader(bufferedReader);
        MapPrinter mapPrinter = new MapPrinter(printWrapper);
        MoveValidator moveValidator = new MoveValidator(gameState, printWrapper);
        Move move = new Move(moveValidator, printWrapper, mapPrinter, randomGenerator);
        CommandHandler commandHandler = new CommandHandler(mapPrinter, move, gameState, printWrapper);
        GameStepPerformer gameStepPerformer = new GameStepPerformer(inputReader, commandHandler, printWrapper);
        GameController gameController = new GameController(gameState, gameStepPerformer, printWrapper);

        gameController.playGame();
    }
}
