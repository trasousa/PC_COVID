package COVID.src.Coronita;

import COVID.src.Exceptions.*;

import javax.naming.NameAlreadyBoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CoronitaClient {
    private Socket socket;
    private BufferedReader inServer;
    private PrintWriter outServer;

    public  CoronitaClient(String host, int port){
        throws IOException {
            this.socket = new Socket(host, port);
            this.inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outServer = new PrintWriter(socket.getOutputStream());
        }
    }
    public void close()
            throws IOException {
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }

    @Override
    public double CreateAcount(String Username, String pass)
            throws InvalidUsername, CoronitaRemotException, InvalidPasswordException {
        outServer.println("cr");
        outServer.flush();
        try {
            return Integer.parseInt(inServer.readLine());

        }
        catch (IOException ignored) {
            throw new CoronitaRemotException("Could not connect to server");
        }
        catch (NumberFormatException ignored){
            throw new CoronitaRemotException("Response format mismatch");
        }
        catch (InvalidUsername e){
            //Username ...
        }
        catch (InvalidPasswordExecption)

    }

        try {
        System.out.println("Is Password "
                + password1 + " valid?");
        isValid(password1);
        System.out.println("Valid Password");
    }

    @Override

    public double CloseAcount(String Username, String pass)
            throws InvalidAcount, CoronitaRemotException {
        outServer.println("rip");
        outServer.flush();
        try {
            return Double.parseDouble(inServer.readLine());
        }
        catch (IOException ignored) {
            throw new CoronitaRemotException("Could not connect to server");
        }
        catch (NumberFormatException ignored){
            throw new CoronitaRemotException("Response format mismatch");
        }
    }

    @Override
    public void UpdateCases(int cases)
            throws InvalidNumCases,  {
        outServer.println();
        outServer.flush();
        try {
            inServer.readLine();
        }
        catch (IOException ignored){
            throw new InvalidNumCases("Not a valid Number");
        }
    }

}
