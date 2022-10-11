package hu.nye.progtech.foxandhounds.ui;

/**
 * Class used to wrap operations that print to stdout.
 */
public class PrintWrapper {

    /**
     * Prints a line to stdout.
     *
     * @param string the string to print
     */
    public void printLine(String string) {
        System.out.println(string);
    }

    /**
     * Prints a line to stdout without line break.
     *
     * @param string the string to print
     */
    public void print(String string) {
        System.out.print(string);
    }
}
