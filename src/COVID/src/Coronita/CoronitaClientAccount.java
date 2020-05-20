package COVID.src.Coronita;


import COVID.src.Exceptions.*;
import COVID.src.Exceptions.AccountExceptions.InvalidAcount;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;
import COVID.src.Exceptions.PasswordExceptions.InvalidPasswordException;
import COVID.src.Exceptions.PasswordExceptions.MismatchPassException;

import java.io.IOException;


public class CoronitaClientAccount {
    //carterio
    CoronitaServer coronita;
    String host;
    int port;

    public CoronitaClientAccount(String s, int i) throws IOException {
        host = "127.0.0.1";
        port = 60833;
        coronita = new CoronitaServer(host,port);
    }

    public void chekUsername (String Username){
        this.coronita.checkUsername(Username);
    }

    public void registerAccount(String Username, String pass1, String pass2) throws AccountException, PasswordException, IOException{
        PasswordVlidator.isValid(pass1);
        if (pass1.equals(pass2)) {
                this.coronita.registerAccount(Username, pass1);
            } else throw new MismatchPassException("Password do not match");
        }

    public void authenticate (String Username, String password) throws InvalidAcount {
        this.coronita.authenticate(Username,password);
        }
     public  void removeAccount (String Username, String password) throws InvalidAcount {
         this.coronita.removeAccount(Username,password);
     }
     public void updateEstimate (String cases) throws InvalidNumCases, AccountException{
         int number = this.isValidNum(cases);
         this.coronita.updateEstimate(number);
     }


    public int isValidNum(String cases) throws  InvalidNumCases{
        int number = Integer.parseInt(cases);
        if(!(number >=0 && number <100))
            throw new InvalidNumCases("Number of cases is not valid");
        else {return number;}
    }

    public void close() throws IOException {
        this.coronita.close();
    }

    public static class PasswordVlidator {
        public static void isValid(String password) throws InvalidPasswordException {
                // for checking if password length is between 8 and 15
                if (!((password.length() >= 8)
                        && (password.length() <= 15))) {
                    // Password length should be between 8 to 15 characters
                    throw new InvalidPasswordException("Password length should be" + " between 8 to 15 characters");
                }
                if (password.contains(" ")) {
                    // Password should not contain any space
                    throw new InvalidPasswordException ("Password should not" + " contain any space");
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
                        throw new InvalidPasswordException ("Password should contain"
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
                    // Password should contain at least one special character ( @, #, %, &, !, $ )
                    throw new InvalidPasswordException ("Password length should be" + " between 8 to 15 characters");
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
                        // Password should contain at least one uppercase letter(A-Z)
                        throw new InvalidPasswordException ("Password should contain at" + " least one uppercase letter(A-Z)");
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
                        // Password should contain at least one lowercase letter(a-z)
                        throw new InvalidPasswordException ("Password should contain at"
                                + " least one lowercase letter(a-z)");
                    }
                }
            }
        }
    }




