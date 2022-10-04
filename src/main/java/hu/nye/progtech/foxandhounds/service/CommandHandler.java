package hu.nye.progtech.foxandhounds.service;

import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.model.Player;
import hu.nye.progtech.foxandhounds.ui.MapPrinter;

public class CommandHandler {
    MapPrinter mapPrinter = new MapPrinter();
    Player player = new Player("Player");

    public void handleCommand(String input, MapVO map) {
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
                System.out.println("*commands*");
                break;

            case "print":
                mapPrinter.printMap(map);
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
