package hu.nye.progtech.foxandhounds.service.input;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component that reads the input from the user.
 */
public class InputReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputReader.class);

    private final BufferedReader reader;

    public InputReader(BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * Reads the user input and returns it as a string.
     *
     * @return the user input as a string
     */
    public String readInput()  {
        String input = null;

        try {
            input = reader.readLine();
        } catch (IOException e) {
            LOGGER.error("Exception occured while reading input");
        }
        return input;
    }
}
