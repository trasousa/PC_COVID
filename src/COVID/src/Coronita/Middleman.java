package COVID.src.Coronita;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Middleman {

    private Socket socket;
    private BufferedReader inServer;

    public Middleman(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }
    public void close()
            throws IOException {
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }
}
