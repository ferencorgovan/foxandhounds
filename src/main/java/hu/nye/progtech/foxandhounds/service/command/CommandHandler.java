package hu.nye.progtech.foxandhounds.service.command;

import java.util.regex.Pattern;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.service.exception.InvalidMoveException;
import hu.nye.progtech.foxandhounds.service.exception.InvalidNameException;
import hu.nye.progtech.foxandhounds.ui.MapPrinter;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used to process input commands.
 */
public class CommandHandler {
    private final MapPrinter mapPrinter;
    private final Move move;
    private final GameState gameState;
    private final PrintWrapper printWrapper;

    public CommandHandler(MapPrinter mapPrinter, Move move, GameState gameState, PrintWrapper printWrapper) {
        this.mapPrinter = mapPrinter;
        this.move = move;
        this.gameState = gameState;
        this.printWrapper = printWrapper;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandHandler.class);
    private static final String MOVE_COMMAND_REGEX = "^move \\d\\d$"; // = [0-9][0-9]
    private static final String NAME_COMMAND_REGEX = "^name .*$";

    /**
     * Handles an input command.
     *
     * @param input the input as a string to be handled
     */
    public void handleCommand(String input) {
        String command = input.split(" ")[0].toLowerCase();

        switch (command) {
            case "name":
                if (!Pattern.matches(NAME_COMMAND_REGEX, input) || input.split(" ").length == 1) {
                    printWrapper.printLine("Invalid name command");
                    throw new InvalidNameException("Invalid name command");
                }
                String name = input.split(" ")[1];
                gameState.getPlayer().setName(name);
                LOGGER.info("Changing player name");
                printWrapper.printLine("Player name changed to " + name);
                break;

            case "help":
                printWrapper.printLine("\nUsable commands: \n- 'name' : Change player name\n" +
                        "- 'print' : Print current state of map\n" +
                        "- 'move [RowIndex][ColumnIndex]' : Move fox (E.g. 'move 61') \n" +
                        "- 'exit' : End the game");
                break;

            case "print":
                mapPrinter.printMap(gameState.getCurrentMap());
                break;

            case "move":
                if (!Pattern.matches(MOVE_COMMAND_REGEX, input)) {
                    printWrapper.printLine("Invalid move");
                    throw new InvalidMoveException("Invalid move!");
                }

                String coordinate = input.split(" ")[1];
                move.foxMove(gameState, coordinate);
                if (gameState.isGameOver()) {
                    printWrapper.printLine("\n" + gameState.getPlayer().getName() + " wins.");
                    break;
                }
                move.enemyMove(gameState);
                break;

            case "exit":
                LOGGER.info("Exiting game");
                printWrapper.printLine("Exiting game");
                gameState.setGameOver(true);
                break;

            default:
                LOGGER.info("Unknown command");
                printWrapper.printLine("Unknown command");
                break;

        }
    }

}
