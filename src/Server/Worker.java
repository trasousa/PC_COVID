package Server;

import Coronita.Interface;
import Exceptions.AccountExceptions.InvalidAccount;
import Exceptions.AccountExceptions.InvalidUsername;
import Exceptions.AccountExceptions.MismatchPassException;
import Server.DataStructures.Accounts;
import Server.DataStructures.Estimates;
import Server.DataStructures.SafePrint;

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
    int readFails;

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
        String read = null;
        try {
            read = in.readLine();
        } catch (IOException e) {
            System.out.println("Server failed to read from input stream!");
            read = "";
            readFails++;
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
                    break;

                case "vw":
                    int cases;
                    country = readParts[1];
                    cases = setCountry(country);
                    if(cases == -1) cases = 0; //serve apenas para enviar 0 casos reportados, quando ainda não reportou
                    out.println("ack " + cases);
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
                readFails = 0;
            } catch (IOException e){
                System.out.println("Failed to read from input stream.");
                if(readFails<5){
                    readFails++;
                    read = "";
                }

                else{
                    read = null;
                    System.out.println(readFails + " consecutive failures when reading from socket input stream, shutting down connection");
                }
            }
        }

        try {
            kill_writer();
            client.shutdownOutput();
            client.shutdownInput();
            System.out.println("Connection terminated.\nAddress: " + client.getInetAddress());
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
    public void removeAccount(String id, String passwd) throws MismatchPassException {
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
        kill_writer();
        currentCountry = country;
        cases = accounts.setCountry(idCliente,country);
        writer.start(idCliente,currentCountry);
        return cases;
    }

    @Override
    public void logout(){
        kill_writer();
    }
}
