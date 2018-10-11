public enum Color {
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

    private Color(String escCode) {
        mEscCode = escCode;
    }

    public String getEscapeCode() {
        return mEscCode;
    }

    public String toString() {
        return getEscapeCode();
    }
}