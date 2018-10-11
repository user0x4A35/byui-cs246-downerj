public class WeakPasswordException extends Exception {
    private static final long serialVersionUID = 55L;

    public WeakPasswordException() {
        super();
    }

    public WeakPasswordException(String message) {
        super(message);
    }
}