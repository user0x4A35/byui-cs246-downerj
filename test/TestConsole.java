import java.util.Scanner;

public class TestConsole {
    private Scanner mIn;

    public TestConsole() {
        mIn = new Scanner(System.in);
    }

    public void one() {
        System.out.print("Enter: ");
        String line = mIn.nextLine();
    }

    public void two() {
        System.out.print("Enter again: ");
        String line = mIn.nextLine();
    }

    public void cleanUp() {
        mIn.close();
    }
    
    public static void main(String[] args) {
        TestConsole test = new TestConsole();
        test.one();
        test.two();
        test.cleanUp();
    }
}