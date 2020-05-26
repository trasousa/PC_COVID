package COVID.src.Server;

import COVID.src.Coronita.Interface;
import COVID.src.Exceptions.AccountExceptions.InvalidAccount;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;
import COVID.src.Exceptions.AccountExceptions.MismatchPassException;
import COVID.src.Server.DataStructures.Accounts;
import COVID.src.Server.DataStructures.Estimate;
import COVID.src.Server.DataStructures.Estimates;
import COVID.src.Server.DataStructures.SafePrint;

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
    Estimates estimates;
    String currentCountry;
    Accounts accounts;
    Writer writer;
    public Worker(Socket client, Estimates estimates, Accounts accounts){
        try {
            this.client = client;
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new SafePrint(new PrintWriter(client.getOutputStream()));
        } catch (IOException e){
            e.printStackTrace();
        }
        this.estimates = estimates;
        this.accounts = accounts;
        writer = new Writer(out,estimates);
    }

    @Override
    public void run(){
        System.out.println("Começou");
        String read = null;
        try {
            read = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String username;
        String password;
        String country;
        while(read != null && !(read.equals("quit"))){
            String[] readParts = read.split("\\s+",2);
            String command = readParts[0];
            System.out.println(command);
            String[] args;

            switch (command){
                case "ck":
                    username = readParts[1];
                    try {
                        checkUsername(username);
                        out.println("ack " + username);
                        System.out.println(username);
                    } catch (InvalidUsername invalidUsername) {
                        out.println("err InvalidUsername");
                    }
                    break;

                case "cr":
                    args = readParts[1].split("\\s+");
                    username = args[0];
                    password = args[1];
                    registerAccount(username,password);
                    out.println("ack cr");
                    break;

                case "lgi":
                    args = readParts[1].split("\\s+");
                    try {
                        username = args[0];
                        password = args[1];
                        authenticate(username,password);
                        idCliente = username;
                        out.println("ack lgi");
                    } catch (InvalidAccount e){
                        out.println("err InvalidAccount");
                    } catch (MismatchPassException e){
                        out.println("err password");
                    }
                    System.out.println("finish!");
                    break;

                case "vw":
                    int cases;
                    kill_writer();
                    currentCountry = readParts[1];
                    cases = accounts.setCountry(idCliente,currentCountry);
                    out.println("ack " + cases);
                    writer.start(idCliente,currentCountry);
                    break;

                case "up":
                    args = readParts[1].split("\\s+");
                    cases = Integer.parseInt(args[0]);
                    updateEstimate(cases);
                    break;
                case "lgo":
                    logout();
                    out.println("ack lgo");
                    break;

                case "rm":
                    args = readParts[1].split("\\s+");
                    try {
                        username = args[0];
                        password = args[1];
                        removeAccount(username,password);
                        out.println("ack rm");
                        kill_writer();
                    } catch (InvalidAccount invalidAccount) {
                        out.println("err InvalidAccount");
                    } catch (MismatchPassException mismatchPass){
                        out.println(("err password"));
                    }
                    break;

                default:
                    out.println("Unknown command");
                    break;
            }

            try{
                read = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            kill_writer();
            client.shutdownOutput();
            client.shutdownInput();
            System.out.println("bye bye");
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void kill_writer(){
        if(currentCountry != null){
            writer.stop();
            estimates.trigger(idCliente,currentCountry);
            writer.join();
        }
    }

    @Override
    public void registerAccount(String id, String passwd){
        accounts.addAccount(id,passwd);
    }

    @Override
    public void authenticate(String id, String passwd) throws InvalidAccount,MismatchPassException{
        accounts.checkPasswd(id,passwd);
    }

    @Override
    public void removeAccount(String id, String passwd) throws InvalidAccount,MismatchPassException {
        accounts.removeAccount(id,passwd);
    }

    @Override
    public void updateEstimate(int cases){
        float newEstimate;
        if (accounts.hasReport(idCliente,currentCountry)){
            newEstimate = accounts.updateCases(idCliente,cases);
            estimates.update(currentCountry,newEstimate);
        }
        else{
            newEstimate = accounts.updateCases(idCliente,cases);
            estimates.firstUpdate(currentCountry,newEstimate);
        }
    }

    @Override
    public void checkUsername(String id) throws InvalidUsername{
        accounts.checkUsername(id);
    }

    @Override
    public int setCountry(String country){
        int cases;
        cases = accounts.setCountry(idCliente,country);
        return cases;
    }

    @Override
    public void logout(){
        kill_writer();
    }
}
