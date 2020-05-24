package COVID.src.Server;

import COVID.src.Coronita.Interface;
import COVID.src.Exceptions.AccountExceptions.InvalidAccount;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;
import COVID.src.Exceptions.AccountExceptions.MismatchPassException;

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
    Estimate currentEstimate;
    Accounts accounts;
    Writer writer;
    public Worker(Socket client, Estimates estimatess, Accounts accounts){
        try {
            this.client = client;
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new SafePrint(new PrintWriter(client.getOutputStream()));
        } catch (IOException e){
            e.printStackTrace();
        }
        this.estimates = estimates;
        this.accounts = accounts;
        writer = new Writer(out);
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
        while(read != null){
            String[] readParts = read.split("\\s+",2);
            String command = readParts[0];
            System.out.println(command);
            String[] args;

            String username;
            String password;
            String country;
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
                    int cases = 0;
                    args = readParts[1].split("\\s+");
                    try {
                        username = args[0];
                        password = args[1];
                        cases = authenticate(username,password);
                        idCliente = username;
                        out.println("ack " + cases);
                    } catch (InvalidAccount e){
                        out.println("err InvalidAccount");
                    } catch (MismatchPassException e){
                        out.println("err password");
                    }
                    break;
                case "up":
                    args = readParts[1].split("\\s+");
                    updateEstimate(Integer.parseInt(args[0]));
                    break;
                case "vw":
                    country = readParts[1];
                    kill_writer();
                    currentEstimate = estimates.getEstimate(country);
                    writer.start(idCliente,currentEstimate);
                    break;
                case "lgo":
                    logout();
                    out.println("ack lgo");
                    break;
                case "rm":
                    args = readParts[1].split("\\s+");
                    try {
                        removeAccount(args[0],args[1]);
                        out.println("ack rm");
                    } catch (InvalidAccount invalidAccount) {
                        out.println("err InvalidAccount");
                    } catch (MismatchPassException mismatchPass){
                        out.println(("err password"));
                    }
                    kill_writer();
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
            client.shutdownInput();
            client.shutdownOutput();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void kill_writer(){
        if(currentEstimate != null){
            writer.stop();
            currentEstimate.trigger();
            writer.join();
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
        currentEstimate.update(newEstimate);
    }

    @Override
    public void checkUsername(String id) throws InvalidUsername{
        accounts.checkUsername(id);
    }
    public void setCountry(String country){
        accounts.setCountry(idCliente,country);
    }
    public void logout(){
        kill_writer();
    }
}
