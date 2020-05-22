package COVID.src.Server;

import COVID.src.Exceptions.AccountExceptions.InvalidAccount;
import COVID.src.Coronita.Interface;
import COVID.src.Exceptions.*;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;
import COVID.src.Exceptions.AccountExceptions.MismatchPassException;
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
                    registerAccount(args[0],args[1]);
                    out.println("ack cr");
                    break;
                case "lg":
                    int cases = 0;
                    args = readParts[1].split("\\s+");
                    try {
                        cases = authenticate(args[0],args[1]);
                        idCliente = args[0];
                        out.println("ack " + cases);
                        writer.start(idCliente);
                    } catch (InvalidAccount e) {
                        out.println("err InvalidAccount");
                    } catch (MismatchPassException e) {
                        out.println("err password");
                    }
                    break;
                case "up":
                    args = readParts[1].split("\\s+");
                    updateEstimate(Integer.parseInt(args[0]));
                    break;
                case "rm":
                    args = readParts[1].split("\\s+");
                    try {
                        removeAccount(args[0],args[1]);
                    } catch (InvalidAccount invalidAccount) {
                        out.println("err InvalidAccount");
                    } catch (MismatchPassException mismatchPass){
                        out.println(("err password"));
                    }
                    break;
                case "ck":
                    String username = readParts[1];
                    try {
                        checkUsername(username);
                        out.println("ck " + username);
                        System.out.println(username);
                    } catch (InvalidUsername invalidUsername) {
                        out.println("err InvalidUsername");
                    }
                    break;
                default:
                    System.out.println("E?");
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
    public void registerAccount(String id, String passwd){
        accounts.addAccount(id,passwd);
    }

    @Override
    public int authenticate(String id, String passwd) throws InvalidAccount,MismatchPassException{
        int cases;
        cases = accounts.checkPasswd(id,passwd);
        return cases;
    }

    @Override
    public void removeAccount(String id, String passwd) throws InvalidAccount,MismatchPassException {
        accounts.removeAccount(id,passwd);
    }

    @Override
    public void updateEstimate(int cases){
        float newEstimate;
        newEstimate = accounts.updateCases(idCliente,cases);
        estimate.update(newEstimate);
    }

    @Override
    public void checkUsername(String id) throws InvalidUsername{
        accounts.checkUsername(id);
    }
}
