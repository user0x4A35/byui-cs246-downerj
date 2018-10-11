public class PasswordPolicy {
    public static boolean validate(char[] password) {
        if (password.length < 8) {
            return false;
        }

        for (char symbol : password) {
            if (Character.isDigit(symbol)) {
                return true;
            }
        }
        return false;
    }
}