package hu.nye.progtech.foxandhounds.service;

import java.util.regex.Pattern;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.Player;
import hu.nye.progtech.foxandhounds.ui.MapPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandler {
    private final MapPrinter mapPrinter;
    private final Move move;
    private final GameState gameState;

    public CommandHandler(MapPrinter mapPrinter, Move move, GameState gameState) {
        this.mapPrinter = mapPrinter;
        this.move = move;
        this.gameState = gameState;
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
                    LOGGER.info("Player name changed to " + name);
                } catch (Exception e) {
                    LOGGER.error("Incorrect name!");
                }
                break;

            case "help":
                System.out.println("\nUsable commands: \n- 'name' : Change player name\n" +
                        "- 'print' : Print current state of map\n" +
                        "- 'move [RowIndexColumnIndex]' : Move fox (E.g. 'move 61') \n" +
                        "- 'exit' : End the game");
                break;

            case "print":
                mapPrinter.printMap(gameState.getCurrentMap());
                break;

            case "move":
                if (!Pattern.matches(MOVE_COMMAND_REGEX, input)) {
                    throw new RuntimeException("Invalid move!");
                }

                String coordinate = input.split(" ")[1];
                try {
                    move.foxMove(gameState, coordinate);
                    mapPrinter.printMap(gameState.getCurrentMap());
                    if (gameState.isGameOver()) {
                        System.out.println("\n" + gameState.getPlayer().getName() + " wins.");
                        break;
                    }
                    move.enemyMove(gameState);
                    System.out.println("Enemy's turn: ");
                    mapPrinter.printMap(gameState.getCurrentMap());
                } catch (RuntimeException e) {
                    LOGGER.error(e.getMessage());
                }
                break;

            case "exit":
                LOGGER.info("Exiting game");
                gameState.setGameOver(true);
                break;

            default:
                LOGGER.info("Unknown command");
                break;

        }
    }

}
