package hu.nye.progtech.foxandhounds.service.input;

import java.util.Scanner;

/**
 * Component that reads the input from the user.
 */
public class InputReader {
    private final Scanner scanner;

    public InputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Reads the user input and returns it as a string.
     *
     * @return the user input as a string
     */
    public String readInput() {
        String input;

        input = scanner.nextLine();
        return input;
    }
}
