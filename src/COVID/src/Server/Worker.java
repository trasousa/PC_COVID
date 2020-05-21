package COVID.src.Server;

import COVID.src.Exceptions.AccountExceptions.InvalidAccount;
import COVID.src.Coronita.Interface;
import COVID.src.Exceptions.*;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;
import COVID.src.Server.Exceptions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Worker implements Runnable, Interface {
    Socket client;
    BufferedReader in;
    SafePrint out;
    String idCliente;
    Estimate estimate;
    Accounts accounts;
    Writer writer;
    public Worker(Socket client, Estimate estimate, Accounts accounts){
        try {
            this.client = client;
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new SafePrint(new PrintWriter(client.getOutputStream()));
        } catch (IOException e){
            e.printStackTrace();
        }
        this.estimate = estimate;
        this.accounts = accounts;
        writer = new Writer(out,estimate);
    }

    @Override
    public void run() {
        System.out.println("Começou");
        String read = null;
        try {
            read = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(!(read.equals("quit")) && read != null){
            String[] readParts = read.split("\\s+",2);
            String command = readParts[0];
            System.out.println(command);
            String[] args;
            switch (command){
                case "cr":
                    args = readParts[1].split("\\s+");
                    try {
                        registerAccount(args[0],args[1]);
                    } catch (InvalidAccount invalidAccount) {
                        out.println("err invalidAccount");
                    }
                    break;
                case "lg":
                    args = readParts[1].split("\\s+");
                    try {
                        authenticate(args[0],args[1]);
                    } catch (InvalidUsername invalidUsername) {
                        out.println("err invalidUsername");
                    } catch (PasswordException e) {
                        out.println("err password");
                    }
                    idCliente = args[0];
                    out.println("ack lg");
                    writer.start(idCliente);
                    break;
                case "up":
                    args = readParts[1].split("\\s+");
                    try {
                        updateEstimate(Integer.parseInt(args[0]));
                    } catch (InvalidAccount invalidAccount) {
                        out.println();
                    }
                    break;
                case "rm":
                    args = readParts[1].split("\\s+");
                    try {
                        removeAccount(args[0],args[1]);
                    } catch (InvalidAccount invalidAccount) {
                        out.println("err invalidAccount");
                    } catch (PasswordException e) {
                        out.println("err password");
                    } catch (InvalidUsername invalidUsername) {
                        out.println(invalidUsername);
                    }
                    break;
                default:
                    System.out.println("E?");
                    out.println("És psycho!");
                    break;
            }
            try {
                read = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            writer.stop();
            client.shutdownInput();
            client.shutdownOutput();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerAccount(String id, String passwd) throws InvalidAccount {
        accounts.addAccount(id,passwd);
    }

    @Override
    public void authenticate(String id, String passwd) throws InvalidAccount{
        accounts.checkPasswd(id,passwd);
    }

    @Override
    public void removeAccount(String id, String passwd) throws InvalidAccount{
        accounts.removeAccount(id,passwd);
    }

    @Override
    public void updateEstimate(int cases){
        estimate.update(idCliente,cases);
    }

    @Override
    public void checkUsername(String Username) throws InvalidUsername{
    }
}
