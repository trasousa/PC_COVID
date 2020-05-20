package COVID.src.Coronita;

import COVID.src.Exceptions.*;
import COVID.src.Exceptions.AccountExceptions.InvalidAcount;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;
import COVID.src.Exceptions.PasswordExceptions.InvalidPasswordException;
import COVID.src.Exceptions.PasswordExceptions.MismatchPassException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CoronitaServer implements Interface {
    private Socket socket;
    private Saco saco;
    private BufferedReader inServer;
    private PrintWriter outServer;


    public  CoronitaServer(String host, int port)
        throws IOException{
            this.socket = new Socket(host, port);
            this.inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outServer = new PrintWriter(socket.getOutputStream());
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
            //espera por saco
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
    public void updateEstimate(String Username, String password){
        outServer.println("up " + Username+ " " + password);
        outServer.flush();
    }
    @Override
    public void checkUsername(String Username) {
        outServer.println("ck " + Username);
        outServer.flush();
    }
}

    public saco .....{
        }

