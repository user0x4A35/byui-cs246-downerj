import java.util.Scanner;
import java.util.NoSuchElementException;

public class Test {
    private Scanner mIn;
    public static Debugger debugger;

    public Test() {
        mIn = new Scanner(System.in);
    }

    public String getPassword() throws Exception {
        String password = "";
        boolean reading = true;

        System.out.print("> Please create a password: ");

        while (reading) {
            if (mIn.hasNextLine()) {
                password = mIn.nextLine().trim();
            } else {
                throw new NoSuchElementException();
            }

            if (!password.isEmpty()) {
                reading = false;
            } else {
                System.out.print("> Please try again: ");
            }
        }

        return password;
    }

    public void displayUserInfo(User user) {
        debugger.printf("Password: %s%n", user.getPassword());
        debugger.printf("Salt:     %s%n", user.getSalt());
        debugger.printf("Hash:     %s%n", user.getHashedPassword());
    }

    public void inputPassword(User user) throws Exception {
        boolean reading = true;

        while (reading) {
            System.out.print("> Enter password: ");

            if (mIn.hasNextLine()) {
                user.setPassword(mIn.nextLine().trim());
            } else {
                throw new NoSuchElementException("No line found");
            }

            if (NSALoginController.verifyPassword(user)) {
                reading = false;
            } else {
                System.out.println("Incorrect password. Please try again.");
            }
        }
    }

    public void cleanUp() {
        mIn.close();
    }

    public static void main(String[] args) {
        int returnCode = 0;
        Test test = new Test();
        boolean reading = true;
        debugger = new Debugger();
        debugger.setColor(Color.GREEN);
        
        while (reading) {
            try {
                String password = test.getPassword();
                User user = new User(password);
                test.displayUserInfo(user);

                debugger.printf("%nHashing password...%n");
                debugger.println("--------------------");

                NSALoginController.hashUserPassword(user);

                debugger.println("Password validated");
                test.displayUserInfo(user);
                test.inputPassword(user);

                debugger.println("Password accepted");
                reading = false;
            } catch (WeakPasswordException wpe) {
                System.out.println("Invalid password. Please try again.");
                reading = true;
            } catch (NoSuchElementException nsee) {
                debugger.setColor(Color.CYAN);
                debugger.printf("%nProgram terminated%n");
                returnCode = 0;
                reading = false;
            } catch (Exception e) {
                debugger.setColor(Color.RED);
                debugger.printf("Error: %s%n", e.getMessage());
                returnCode = 1;
                reading = false;
            }
        }

        test.cleanUp();
        System.exit(returnCode);
    }
}