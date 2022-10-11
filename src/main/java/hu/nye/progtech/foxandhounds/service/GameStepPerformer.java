package hu.nye.progtech.foxandhounds.service;

import hu.nye.progtech.foxandhounds.service.input.InputReader;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameStepPerformer {
    private final InputReader inputReader;
    private final CommandHandler commandHandler;
    private final PrintWrapper printWrapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(GameStepPerformer.class);

    public GameStepPerformer(InputReader inputReader, CommandHandler commandHandler, PrintWrapper printWrapper) {
        this.inputReader = inputReader;
        this.commandHandler = commandHandler;
        this.printWrapper = printWrapper;
    }

    public void performGameStep() {
        printWrapper.printLine("\nEnter a command: ");
        String input = inputReader.readInput();
        try {
            commandHandler.handleCommand(input);
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
