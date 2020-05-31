package Coronita;

import Exceptions.AccountException;
import Exceptions.AccountExceptions.InvalidAccount;
import Exceptions.AccountExceptions.InvalidUsername;
import Exceptions.AccountExceptions.MismatchPassException;
import javafx.scene.Scene;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CoronitaServer implements Interface {
    private String answer[];
    private Socket socket;
    private Middleman dealer;
    private Bag bag;
    private BufferedReader inServer;
    private PrintWriter outServer;


    public  CoronitaServer(String host, int port, JTextField estimateglobal, JTextField estimatecountry, Scene scene)
        throws IOException{
            this.socket = new Socket(host, port);
            this.outServer = new PrintWriter(socket.getOutputStream());
            this.inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bag = new Bag();
            this.dealer = new Middleman(inServer,bag,estimateglobal,estimatecountry, scene);
            dealer.start();
        }

    public void close()
            throws IOException {
        socket.shutdownOutput();
        dealer.join();
        socket.shutdownInput();
        socket.close();
    }

    @Override
    public void checkUsername(String Username) throws InvalidUsername{
        outServer.println("ck " + Username);
        outServer.flush();
        answer = this.bag.getLetter();
        if(answer[0].equals("err")){
            if(answer[1].equals("InvalidUsername")) throw new InvalidUsername("InvalidUsername");
        }
    }

    @Override
    public void registerAccount(String Username, String password){
        outServer.println("cr " + Username + " " + password);
        outServer.flush();
        answer = this.bag.getLetter();
        if(answer[0].equals("ack")){
            if(answer[1].equals("Sucsses")) System.out.println("registado");
        }
    }

    @Override
    public void authenticate(String Username, String password) throws InvalidAccount, MismatchPassException{
        outServer.println("lgi " + Username + " " + password);
        outServer.flush();
        answer = this.bag.getLetter();
        if (answer[0].equals("err")) {
            if (answer[1].equals("InvalidAccount")) throw new InvalidAccount("InvalidAccount");
            if (answer[1].equals("password")) throw new MismatchPassException("Incorrect Password");
        }
    }
    @Override
    public void removeAccount (String Username, String password) throws AccountException {
        outServer.println("rm " + Username + " " + password);
        outServer.flush();
        answer = this.bag.getLetter();
        if (answer[0].equals("err")) {
            if (answer[1].equals("InvalidAccount")) throw new InvalidAccount("InvalidAccount");
        }
    }

    @Override
    public void updateEstimate (int cases){
        outServer.println("up " + cases);
        outServer.flush();
    }

    @Override
    public int setCountry(String country){
        int a = 0;
        outServer.println("vw " + country);
        outServer.flush();
        answer = this.bag.getLetter();
        if (answer[0].equals("ack")) {
            a = Integer.parseInt(answer[1]);
        }
    return a;
    }
    @Override
    public void logout(){
        outServer.println("lgo");
        outServer.flush();
        answer = this.bag.getLetter();
    }

}



