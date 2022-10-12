package hu.nye.progtech.foxandhounds.service.game;

import hu.nye.progtech.foxandhounds.service.command.CommandHandler;
import hu.nye.progtech.foxandhounds.service.input.InputReader;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component that performs a game step.
 */
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

    /**
     * Performs a game step.
     */
    public void performGameStep() {
        printWrapper.print("\nEnter a command: ");
        String input = inputReader.readInput();
            commandHandler.handleCommand(input);
    }
}
