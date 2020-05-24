package COVID.src.terminal;

import COVID.src.Coronita.CoronitaServer;
import COVID.src.Exceptions.AccountExceptions.InvalidAccount;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;
import COVID.src.Exceptions.AccountExceptions.MismatchPassException;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;

public class terminalClient{

    public static void main(String args[]) throws IOException {
        String host = "127.0.0.1";
        int port = 60833;
        CoronitaServer stub = new CoronitaServer(host,port,null,null);
        Console con = System.console();
        PrintWriter out = con.writer();

        String read = "";
        String comando;
        read = con.readLine("You want to register or login?");
        switch (read) {
            case "r":
            case "register":
                String username = con.readLine("Username:");
                try {
                    stub.checkUsername(username);
                } catch (InvalidUsername invalidUsername) {
                    out.println("Username " + username + " is already in use!");
                }
                char[] passwd = con.readPassword("Enter your password 2 times");
                char[] passwd2 = con.readPassword();
                if(passwd.equals(passwd2)){
                    stub.registerAccount(username,new String(passwd));
                }
                else System.err.println("Passwords must be the same");
                break;
            case "l":
            case "login":
                username = con.readLine("Username:");
                passwd = con.readPassword("Password:");
                try {
                    stub.authenticate(username,new String(passwd));
                } catch(InvalidAccount ia) {
                  out.println("A conta não existe");
                } catch (MismatchPassException e) {
                    e.printStackTrace();
                }
        }
    }
}
