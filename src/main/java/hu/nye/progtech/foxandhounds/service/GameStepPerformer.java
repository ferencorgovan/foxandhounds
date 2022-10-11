package hu.nye.progtech.foxandhounds.service;

import hu.nye.progtech.foxandhounds.service.input.InputReader;

public class GameStepPerformer {
    private final InputReader inputReader;
    private final CommandHandler commandHandler;

    public GameStepPerformer(InputReader inputReader, CommandHandler commandHandler) {
        this.inputReader = inputReader;
        this.commandHandler = commandHandler;
    }

    public void performGameStep() {
        System.out.print("\nEnter a command: ");
        String input = inputReader.readInput();
        try {
            commandHandler.handleCommand(input);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
