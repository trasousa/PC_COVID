package COVID.src.Coronita;

import COVID.src.Exceptions.AccountException;
import COVID.src.Exceptions.AccountExceptions.InvalidAccount;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class CoronitaServer implements Interface {
    private String answer[];
    private Socket socket;
    private Middleman dealer;
    private Bag bag;
    private PrintWriter outServer;


    public  CoronitaServer(String host, int port)
        throws IOException{
            this.socket = new Socket(host, port);
            this.outServer = new PrintWriter(socket.getOutputStream());
            this.dealer = new Middleman(socket);
            this.bag = new Bag();
        }

    public void close()
            throws IOException {
        socket.shutdownOutput();
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
            //if(answer[1].equals("Sucsses")) ;
        }
    }

    @Override
    public int authenticate(String Username, String password) throws InvalidAccount {
        int a = 0;
        outServer.println("lg " + Username + " " + password);
        outServer.flush();
        answer = this.bag.getLetter();
        if (answer[0].equals("err")) {
            a = -1;
            if (answer[1].equals("InvalidAccount")) throw new InvalidAccount("InvalidAccount");
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
    public void updateEstimate ( int cases){
        outServer.println("up " + cases);
        outServer.flush();
        answer = this.bag.getLetter();
    }

}



