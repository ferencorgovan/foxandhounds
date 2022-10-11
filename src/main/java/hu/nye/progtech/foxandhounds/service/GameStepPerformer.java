package hu.nye.progtech.foxandhounds.service;

import hu.nye.progtech.foxandhounds.service.input.InputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameStepPerformer {
    private final InputReader inputReader;
    private final CommandHandler commandHandler;
    private static final Logger LOGGER = LoggerFactory.getLogger(GameStepPerformer.class);

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
            LOGGER.error(e.getMessage());
        }
    }
}
