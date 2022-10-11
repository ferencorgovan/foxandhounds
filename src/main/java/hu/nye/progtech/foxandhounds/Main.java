package hu.nye.progtech.foxandhounds;

import java.util.Scanner;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.model.Player;
import hu.nye.progtech.foxandhounds.service.CommandHandler;
import hu.nye.progtech.foxandhounds.service.GameController;
import hu.nye.progtech.foxandhounds.service.GameStepPerformer;
import hu.nye.progtech.foxandhounds.service.MapGenerator;
import hu.nye.progtech.foxandhounds.service.Move;
import hu.nye.progtech.foxandhounds.service.MoveValidator;
import hu.nye.progtech.foxandhounds.service.input.InputReader;
import hu.nye.progtech.foxandhounds.ui.MapPrinter;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;

public class Main {

    public static void main(String[] args) {
        PrintWrapper printWrapper = new PrintWrapper();
        MapGenerator mapGenerator = new MapGenerator();
        MapVO mapVO = mapGenerator.generateMap(8);
        Player player = new Player("Player");
        GameState gameState = new GameState(mapVO, false, player);
        InputReader inputReader = new InputReader(new Scanner(System.in));
        MapPrinter mapPrinter = new MapPrinter(printWrapper);
        MoveValidator moveValidator = new MoveValidator();
        Move move = new Move(moveValidator, printWrapper);
        CommandHandler commandHandler = new CommandHandler(mapPrinter, move, gameState, printWrapper);
        GameStepPerformer gameStepPerformer = new GameStepPerformer(inputReader, commandHandler, printWrapper);
        GameController gameController = new GameController(gameState, gameStepPerformer, printWrapper);

        gameController.playGame();
    }
}
