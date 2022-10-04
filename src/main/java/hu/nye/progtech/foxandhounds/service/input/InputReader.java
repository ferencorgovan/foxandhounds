package hu.nye.progtech.foxandhounds.service.input;

import java.util.Scanner;

public class InputReader {
    private final Scanner scanner;

    public InputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readInput() {
        String input;

        input = scanner.nextLine();
        return input;
    }
}
