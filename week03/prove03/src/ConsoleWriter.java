/**
 * This is a wrapper for {@link java.lang.System.out} and
 * {@link java.lang.System.err} using ANSI color formatting codes.
 * 
 * @author James D. Downer
 * @since October 4th, 2018
 * @version 1.0
 */
public class ConsoleWriter {

    private ConsoleColor mColor;

    /**
     * Constructs the ConsoleWriter.
     */
    public ConsoleWriter() {
        resetColor();
    }

    /**
     * Sets the current ANSI color.
     * @param color The desired {@link ConsoleColor}
     */
    public void setColor(ConsoleColor color) {
        mColor = color;
    }

    /**
     * Sets the current ANSI color to {@link ConsoleColor.RESET}.
     */
    public void resetColor() {
        mColor = ConsoleColor.RESET;
    }

    /**
     * A wrapper for {@link java.lang.System.out.printf}.
     * @param format The format {@link java.lang.String}
     * @param args Optional formatting arguments
     */
    public void printf(String format, Object ... args) {
        System.out.printf(mColor.toString());
        System.out.printf(format, args);
        System.out.printf(ConsoleColor.RESET.toString());
    }
    
    /**
     * A wrapper for {@link java.lang.System.out.println}.
     * @param string The text {@link java.lang.String}
     */
    public void println(String string) {
        System.out.printf(mColor.toString());
        System.out.println(string);
        System.out.printf(ConsoleColor.RESET.toString());
    }

    /**
     * Prints a number of blank lines.
     * @param numLines The number of lines to print
     */
    public void println(int numLines) {
        for (int n = 0; n < numLines; n++) {
            System.out.println();
        }
    }

    /**
     * A wrapper for {@link java.lang.System.err.printf} using
     * the color yellow ({@link ConsoleColor.YELLOW}).
     * @param format The format {@link java.lang.String}
     * @param args Optional formatting arguments
     */
    public void warningf(String format, Object ... args) {
        System.err.printf(ConsoleColor.YELLOW.toString());
        System.err.printf(format, args);
        System.err.printf(ConsoleColor.RESET.toString());
    }

    /**
     * A wrapper for {@link java.lang.System.err.println} using
     * the color yellow ({@link ConsoleColor.YELLOW}).
     * @param string The text {@link java.lang.String}
     */
    public void warningln(String string) {
        System.err.printf(ConsoleColor.YELLOW.toString());
        System.err.println(string);
        System.err.printf(ConsoleColor.RESET.toString());
    }

    /**
     * A wrapper for {@link java.lang.System.err.printf} using
     * the color red ({@link ConsoleColor.RED}).
     * @param format The format {@link java.lang.String}
     * @param args Optional formatting arguments
     */
    public void errorf(String format, Object ... args) {
        System.err.printf(ConsoleColor.RED.toString());
        System.err.printf(format, args);
        System.err.printf(ConsoleColor.RESET.toString());
    }

    /**
     * A wrapper for {@link java.lang.System.out.println} using
     * the color red ({@link ConsoleColor.RED}).
     * @param string The text {@link java.lang.String}
     */
    public void errorln(String string) {
        System.err.printf(ConsoleColor.RED.toString());
        System.err.println(string);
        System.err.printf(ConsoleColor.RESET.toString());
    }
}