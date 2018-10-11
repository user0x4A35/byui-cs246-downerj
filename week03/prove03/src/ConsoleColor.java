/**
 * This enumeration encapsulates ANSI color formatting codes
 * for Unix-style terminals.
 * 
 * @author James D. Downer
 * @since October 4th, 2018
 * @version 1.0
 */
public enum ConsoleColor {

    BLACK ("\u001b[1;30m"),
    RED ("\u001b[1;31m"),
    GREEN ("\u001b[1;32m"),
    YELLOW ("\u001b[1;33m"),
    BLUE ("\u001b[1;34m"),
    MAGENTA ("\u001b[1;35m"),
    CYAN ("\u001b[1;36m"),
    WHITE ("\u001b[1;37m"),
    DEFAULT ("\u001b[1;39m"),
    RESET ("\u001b[0m");

    private final String mEscCode;

    /**
     * Constructs the ConsoleColor.
     * @param escCode The ANSI code {@link java.lang.String}
     */
    private ConsoleColor(String escCode) {
        mEscCode = escCode;
    }

    /**
     * Returns the ANSI code {@link java.lang.String}.
     * @return The ANSI code
     */
    public String getEscapeCode() {
        return mEscCode;
    }

    /**
     * Returns the ANSI code {@link java.lang.String}.
     * @return The ANSI code
     */
    @Override
    public String toString() {
        return getEscapeCode();
    }
}