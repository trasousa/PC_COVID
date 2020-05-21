package COVID.src.Coronita;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Middleman implements Runnable{

    private BufferedReader inServer;

    public Middleman(String host, int port, Socket socket) throws IOException {
        this.inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }
    public int dealCases(String cenas){
        int number;
        String[] readParts = cenas.split("\\s+");
        if(readParts[0].equals("est")){
            number = Integer.parseInt(readParts[1]);
        }
        else
    }
}

