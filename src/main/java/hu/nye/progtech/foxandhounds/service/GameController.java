package hu.nye.progtech.foxandhounds.service;

import java.util.Scanner;

import hu.nye.progtech.foxandhounds.model.MapVO;

public class GameController {
    MapVO mapVO;

    public GameController(MapVO mapVO) {
        this.mapVO = mapVO;
    }

    CommandHandler commandHandler = new CommandHandler();

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Game started! Type 'help' for commands.");
        String input;
        do {
            System.out.print("Enter a command: ");
            input = scanner.nextLine();
            commandHandler.handleCommand(input, mapVO);
        } while (!input.equals("exit"));
    }
}
