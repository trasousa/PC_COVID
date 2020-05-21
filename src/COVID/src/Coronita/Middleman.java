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

    public void run(){
        while(true) {
            try {
                // read the message form the input datastream
                String msg = (String) inServer.readObject();
                // print the message
                System.out.println(msg);
                System.out.print("> ");
            }
            catch(IOException e) {
                display(notif + "Server has closed the connection: " + e + notif);
                break;
            }
            catch(ClassNotFoundException e2) {
            }
        }
    }
}


