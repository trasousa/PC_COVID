package COVID.src.Coronita;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class CoronitaServer implements Interface {

    private Socket socket;
    private Middleman dealer;
    private PrintWriter outServer;


    public  CoronitaServer(String host, int port)
        throws IOException{
            this.socket = new Socket(host, port);
            this.outServer = new PrintWriter(socket.getOutputStream());
            this.dealer = new Middleman(host ,port, socket);
        }

    public void close()
            throws IOException {
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }

    @Override
    public void registerAccount(String Username, String password){
        outServer.println("cr " + Username + " " + password);
        outServer.flush();

    }

    @Override
    public void authenticate(String Username, String password) {
        outServer.println("lg " + Username+ " " + password);
        outServer.flush();
    }

    @Override
    public void removeAccount(String Username, String password) {
        outServer.println("rm " + Username+ " " + password);
        outServer.flush();
    }

    @Override
    public void updateEstimate(int cases){
        outServer.println("up " + cases);
        outServer.flush();
    }
    @Override
    public void checkUsername(String Username) {
        outServer.println("ck " + Username);
        outServer.flush();
    }

    public void dealCases(){
        this.dealer();
    }

}


