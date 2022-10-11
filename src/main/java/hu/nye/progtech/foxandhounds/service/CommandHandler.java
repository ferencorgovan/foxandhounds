package hu.nye.progtech.foxandhounds.service;

import java.util.regex.Pattern;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.ui.MapPrinter;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final String PRINT_COMMAND_REGEX = "print";
    private static final String MOVE_COMMAND_REGEX = "^move [0-7][0-7]$";

    public void handleCommand(String input) {
        String command = input.split(" ")[0].toLowerCase();

        switch (command) {
            case "name":
                try {
                    String name = input.split(" ")[1];
                    gameState.getPlayer().setName(name);
                    LOGGER.info("Changing player name");
                    printWrapper.printLine("Player name changed to " + name);
                } catch (Exception e) {
                    LOGGER.error("Invalid name!");
                }
                break;

            case "help":
                printWrapper.printLine("\nUsable commands: \n- 'name' : Change player name\n" +
                        "- 'print' : Print current state of map\n" +
                        "- 'move [RowIndexColumnIndex]' : Move fox (E.g. 'move 61') \n" +
                        "- 'exit' : End the game");
                break;

            case "print":
                mapPrinter.printMap(gameState.getCurrentMap());
                break;

            case "move":
                if (!Pattern.matches(MOVE_COMMAND_REGEX, input)) {
                    printWrapper.printLine("Invalid move");
                    throw new RuntimeException("Invalid move!");
                }

                String coordinate = input.split(" ")[1];
                try {
                    move.foxMove(gameState, coordinate);
                    mapPrinter.printMap(gameState.getCurrentMap());
                    if (gameState.isGameOver()) {
                        printWrapper.printLine("\n" + gameState.getPlayer().getName() + " wins.");
                        break;
                    }
                    move.enemyMove(gameState);
                    printWrapper.printLine("Enemy's turn: ");
                    mapPrinter.printMap(gameState.getCurrentMap());
                } catch (RuntimeException e) {
                    LOGGER.error(e.getMessage());
                }
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
