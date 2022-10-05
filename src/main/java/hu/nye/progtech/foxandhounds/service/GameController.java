package hu.nye.progtech.foxandhounds.service;

import java.util.Scanner;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;

public class GameController {
    MapVO mapVO;
    GameState gameState;

    public GameController(GameState gameState, MapVO mapVO) {
        this.gameState = gameState;
        this.mapVO = mapVO;
    }

    CommandHandler commandHandler = new CommandHandler();

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nGame started! Type 'help' for commands.");
        String input;
        do {
            System.out.print("\nEnter a command: ");
            input = scanner.nextLine();
            commandHandler.handleCommand(input, gameState, mapVO);
        } while (!input.equals("exit"));
    }
}
