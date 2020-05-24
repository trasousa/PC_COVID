package COVID.src.Coronita;

import COVID.src.Exceptions.AccountException;
import COVID.src.Exceptions.AccountExceptions.InvalidAccount;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;
import COVID.src.Exceptions.AccountExceptions.MismatchPassException;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class CoronitaServer implements Interface {
    private String answer[];
    private Socket socket;
    private Middleman dealer;
    private Bag bag;
    private PrintWriter outServer;


    public  CoronitaServer(String host, int port, JTextField estimateglobal, JTextField estimatecountry)
        throws IOException{
            this.socket = new Socket(host, port);
            this.outServer = new PrintWriter(socket.getOutputStream());
            this.bag = new Bag();
            this.dealer = new Middleman(socket,bag,estimateglobal,estimatecountry);
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
            if(answer[1].equals("Sucsses")) ;
        }
    }

    @Override
    public int authenticate(String Username, String password) throws InvalidAccount, MismatchPassException{
        int a = 0;
        outServer.println("lgi " + Username + " " + password);
        outServer.flush();
        answer = this.bag.getLetter();
        if (answer[0].equals("err")) {
            if (answer[1].equals("InvalidAccount")) throw new InvalidAccount("InvalidAccount");
            if (answer[1].equals("password")) throw new MismatchPassException("Incorrect Password");
        }
        return a;
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
    public void setCountry(String country){
        outServer.println("vw" + country);
        outServer.flush();

    };
    @Override
    public void logout(){
        outServer.println("lgo");
        outServer.flush();
        answer = this.bag.getLetter();
    };

}



