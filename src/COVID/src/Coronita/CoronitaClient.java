package COVID.src.Coronita;

import COVID.src.Exceptions.*;
import COVID.src.Exceptions.AccountExceptions.InvalidAcount;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;
import COVID.src.Exceptions.PasswordExceptions.InvalidPasswordException;
import COVID.src.Exceptions.PasswordExceptions.MismatchPassException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CoronitaClient {
    private Socket socket;
    private BufferedReader inServer;
    private PrintWriter outServer;

    public  CoronitaClient(String host, int port){
        throws IOException{
            this.socket = new Socket(host, port);
            this.inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outServer = new PrintWriter(socket.getOutputStream());
        }
    }
    public void close()
            throws IOException {
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }

    @Override
    public Double registerClient(String Username, String pass1, String pass2)
            throws InvalidUsername, CoronitaRemotException{
        outServer.println("cr");
        outServer.flush();
        try {
            PasswordvVlidator.isValid(pass1);
            EqualPass.areEqual(pass1,pass2);
            //UserValidator.isValid(Username);
            return Double.parseDouble(inServer.readLine());
        } catch (IOException ignored) {
            throw new CoronitaRemotException("Could not connect to server");
        } catch (NumberFormatException ignored) {
            throw new CoronitaRemotException("Response format mismatch");
        } catch (InvalidPasswordException e) {
            e.printStackTrace();
        } catch (MismatchPassException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Double authenticate(String Username, String password)
            throws InvalidUsername, CoronitaRemotException {
        outServer.println("lg");
        outServer.flush();
        try {
            PasswordvVlidator.isValid(password);
            //UserValidator.isValid(Username);
            return Double.parseDouble(inServer.readLine());
        } catch (IOException ignored) {
            throw new CoronitaRemotException("Could not connect to server");
        } catch (NumberFormatException ignored) {
            throw new CoronitaRemotException("Response format mismatch");
        } catch (InvalidPasswordException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override

    public double removeClient(String Username, String pass)
            throws InvalidAcount, CoronitaRemotException, InvalidUsername{
        outServer.println("rm");
        outServer.flush();
        try {
            return Double.parseDouble(inServer.readLine());
        }
        catch (IOException ignored) {
            throw new CoronitaRemotException("Could not connect to server");
        }
        catch (NumberFormatException ignored){
            throw new CoronitaRemotException("Response format mismatch");
        }
    }

    @Override
    public void updateEstimate(int cases)
            throws InvalidNumCases {
        outServer.println("up");
        outServer.flush();
        try {
            inServer.readLine();
        }
        catch (IOException ignored){
            throw new InvalidNumCases("Not a valid Number");
        }
    }
    public static class EqualPass{
        public static void areEqual(String password1, String password2)
            throws MismatchPassException{
            if(!(password1.equals(password2))){
                throw new MismatchPassException("Incorrect Password, try again");
            }
        }
    }

    public static class PasswordvVlidator {
        public static void isValid(String password)
                throws InvalidPasswordException {
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
