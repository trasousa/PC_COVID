package Coronita;


import Exceptions.AccountException;
import Exceptions.AccountExceptions.InvalidAccount;
import Exceptions.AccountExceptions.InvalidUsername;
import Exceptions.AccountExceptions.MismatchPassException;
import Exceptions.InvalidNumCases;
import Exceptions.PasswordExceptions.InvalidPasswordException;
import javafx.scene.Scene;

import javax.swing.*;
import java.io.IOException;


public class CoronitaClientAccount {

    CoronitaServer coronita;
    String host;
    int port;
    private static JTextField estimateglobal;
    private static JTextField estimatecountry;

    public CoronitaClientAccount(JTextField estimateglobal, JTextField estimatecountry, Scene scene) throws IOException {
        this.estimateglobal = estimateglobal;
        this.estimatecountry = estimatecountry;
        host = "127.0.0.1";
        port = 60833;
        coronita = new CoronitaServer(host,port,estimateglobal,estimatecountry,scene);
    }

    public void chekUsername (String Username) throws InvalidUsername {
        this.coronita.checkUsername(Username);
    }

    public void registerAccount(String Username, String pass1, String pass2) throws InvalidPasswordException, MismatchPassException{
        PasswordVlidator.isValid(pass1);
        if (pass1.equals(pass2)) {
                this.coronita.registerAccount(Username, pass1);
            } else throw new MismatchPassException("Password do not match");
        }

    public void authenticate (String Username, String password) throws InvalidAccount, MismatchPassException {
        this.coronita.authenticate(Username,password);

    }
     public  void removeAccount (String Username, String password) throws AccountException {
         this.coronita.removeAccount(Username,password);
     }
     public void updateEstimate (String cases) throws InvalidNumCases{
         int number = this.isValidNum(cases);
         this.coronita.updateEstimate(number);
     }

    public int isValidNum(String cases) throws  InvalidNumCases{
        int number = Integer.parseInt(cases);
        if(!(number >=0 && number <= 150))
            throw new InvalidNumCases("Number of cases is not valid");
        else {return number;}
    }

    public int setCountry(String country){
        int a = this.coronita.setCountry(country);
        return a;
    }
    public void logout(){
        this.coronita.logout();
    }

    public void close() throws IOException {
        this.coronita.close();
    }

    public static class PasswordVlidator {
        public static void isValid(String password) throws InvalidPasswordException {
                // for checking if password length is between 8 and 15
                if (!((password.length() >= 3)
                        && (password.length() <= 10))) {
                    // Password length should be between 3 to 10 characters
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
        }
    }
}





