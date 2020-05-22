package COVID.src.terminal;

import COVID.src.Coronita.CoronitaServer;
import COVID.src.Exceptions.AccountExceptions.InvalidAccount;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;
import COVID.src.Exceptions.AccountExceptions.MismatchPassException;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class terminalClient{

    public static void main(String args[]) throws IOException {
        String host = "127.0.0.1";
        int port = 60833;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        CoronitaServer stub = new CoronitaServer(host,port);
        Console con = System.console();

        String read = "";
        String comando;
        read = con.readLine("You want to register or login?");
        switch (read) {
            case "r":
            case "register":
                System.out.println("Username:");
                String username = input.readLine();
                char[] pass1 = con.readPassword("Enter your password 2 times");
                char[] pass2 = con.readPassword();
                if(pass1.equals(pass2)){
                    stub.registerAccount(username,new String(pass1));
                }
                else System.err.println("Passwords must be the same");
        }
        String[] readParts = read.split("\\s+");
        comando = readParts[0];
        switch (comando) {
            case "register":
                if(readParts.length == 3){
                    String username = readParts[1];
                    String password = readParts[2];
                    try {
                        stub.checkUsername("username");
                        stub.registerAccount(username,password);
                    } catch (InvalidUsername invalidUsername) {
                        System.err.println("O username não está disponivel");
                    }
                }
                else
                    System.err.println("Insira o nome e a palavra passe");
                break;
            case "lg":

                break;
            case "up":
                break;
            case "rm":
                break;
            case "ck":
                break;
            default:
                break;
        }
    }

    }
