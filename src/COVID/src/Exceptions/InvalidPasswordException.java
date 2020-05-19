package COVID.src.Exceptions;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String error)
    {
        super(error);
    }

    public static class PasswordvVlidator {

        // A utility function to check
        // whether a password is valid or not
        public static String isValid(String password)
                throws InvalidPasswordException {

            // for checking if password length
            // is between 8 and 15
            if (!((password.length() >= 8)
                    && (password.length() <= 15))) {
                // Password length should be
                // between 8 to 15 characters
                return ("Password length should be"
                        + " between 8 to 15 characters");
            }

            // to check space
            if (password.contains(" ")) {
                // Password should not contain any space
                return ("Password should not"
                        + " contain any space");
            }
            if (true) {
                int count = 0;

                // check digits from 0 to 9
                for (int i = 0; i <= 9; i++) {

                    // to convert int to string
                    String str1 = Integer.toString(i);

                    if (password.contains(str1)) {
                        count = 1;
                    }
                }
                if (count == 0) {
                    // Password should contain// at least one digit(0-9)
                    return ("Password should contain"
                            + " at least one digit(0-9)");
                }
            }

            // for special characters
            if (!(password.contains("@") || password.contains("#")
                    || password.contains("!") || password.contains("~")
                    || password.contains("$") || password.contains("%")
                    || password.contains("^") || password.contains("&")
                    || password.contains("*") || password.contains("(")
                    || password.contains(")") || password.contains("-")
                    || password.contains("+") || password.contains("/")
                    || password.contains(":") || password.contains(".")
                    || password.contains(", ") || password.contains("<")
                    || password.contains(">") || password.contains("?")
                    || password.contains("|"))) {
                // Password should contain at least
                // one special character ( @, #, %, &, !, $ )
                return ("Password length should be"
                        + " between 8 to 15 characters");
            }

            if (true) {
                int count = 0;

                // checking capital letters
                for (int i = 65; i <= 90; i++) {

                    // type casting
                    char c = (char) i;

                    String str1 = Character.toString(c);
                    if (password.contains(str1)) {
                        count = 1;
                    }
                }
                if (count == 0) {
                    // Password should contain at least
                    // one uppercase letter(A-Z)
                    return ("Password should contain at"
                            + " least one uppercase letter(A-Z)");
                }
            }
            if (true) {
                int count = 0;

                // checking small letters
                for (int i = 90; i <= 122; i++) {

                    // type casting
                    char c = (char) i;
                    String str1 = Character.toString(c);

                    if (password.contains(str1)) {
                        count = 1;
                    }
                }
                if (count == 0) {
                    // Password should contain at least
                    // one lowercase letter(a-z)
                    return ("Password should contain at"
                            + " least one lowercase letter(a-z)");
                }
            }
            // The password is valid

            return ("");
        }
    }
}