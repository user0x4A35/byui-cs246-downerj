public class Debugger {
    private Color mColor;

    public Debugger() {
        resetColor();
    }

    public void setColor(Color color) {
        mColor = color;
    }

    public void resetColor() {
        mColor = Color.RESET;
    }

    public void printf(String format, Object ... args) {
        System.out.printf(mColor.toString());
        System.out.printf(format, args);
        System.out.printf(Color.RESET.toString());
    }
    
    public void println(String string) {
        System.out.printf(mColor.toString());
        System.out.println(string);
        System.out.printf(Color.RESET.toString());
    }
}