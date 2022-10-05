package hu.nye.progtech.foxandhounds.service;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.model.Player;
import hu.nye.progtech.foxandhounds.ui.MapPrinter;

public class CommandHandler {
    private final MapPrinter mapPrinter = new MapPrinter();
    private final Player player = new Player("Player");
    private final Move move = new Move();

    public void handleCommand(String input, GameState gameState, MapVO map) {
        String command = input.split(" ")[0].toLowerCase();

        switch (command) {
            case "name":
                try {
                    String name = input.split(" ")[1];
                    player.setName(name);
                    System.out.println("Player name changed to " + name);
                } catch (Exception e) {
                    System.out.println("Incorrect name!");
                }
                break;

            case "help":
                System.out.println("\nUsable commands: \n- 'name' : Change player name\n" +
                                    "- 'print' : Print current state of map\n" +
                                    "- 'move [RowIndexColumnIndex]' : Move fox (E.g. 'move 61') \n" +
                                    "- 'exit' : End the game");
                break;

            case "print":
                mapPrinter.printMap(map);
                break;

            case "move":
                String coordinate = input.split(" ")[1];
                try {
                    gameState.setCurrentMap(move.foxMove(map, gameState, coordinate));
                    mapPrinter.printMap(gameState.getCurrentMap());
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
                //System.out.println("Enemy's turn: ");
                //gameState.setCurrentMap(move.enemyMove(map, gameState));
                //mapPrinter.printMap(gameState.getCurrentMap());
                break;

            case "exit":
                System.exit(0);
                break;

            default:
                System.out.println("Unknown command");
                break;

        }
    }

}
