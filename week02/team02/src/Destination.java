public enum Destination {
    MEXICO (1), EUROPE (2), JAPAN (3);

    private final int code;

    private Destination(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }
}