package COVID.src.Coronita;

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
    public void registerAccount(String Username, String password) throws InvalidUsername {
        outServer.println("cr " + Username + " " + password);
        outServer.flush();
        try {
            answer = this.bag.getLetter();
        }
        catch (InvalidUsername e){

        }
    }

    @Override
    public void authenticate(String Username, String password) throws InvalidAccount{
        outServer.println("lg " + Username+ " " + password);
        outServer.flush();
        answer = this.bag.getLetter();
        //ver se é preciso atirar
    }

    @Override
    public void removeAccount(String Username, String password) throws InvalidAccount{
        outServer.println("rm " + Username+ " " + password);
        outServer.flush();
        answer = this.bag.getLetter();
        if(answer[0].equals("err")){
            // if answer[1] e fazer throw de Exception de acordo
        }
    }

    @Override
    public void updateEstimate(int cases){
        outServer.println("up " + cases);
        outServer.flush();
        answer = this.bag.getLetter();
    }

    @Override
    public void checkUsername(String Username) throws InvalidUsername{
        outServer.println("ck " + Username);
        outServer.flush();
        answer = this.bag.getLetter();
    }
}


