package COVID.src.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
	ServerSocket sSock = new ServerSocket( 	60833);
	while(true){
        Socket cliente = sSock.accept();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter out = new PrintWriter(cliente.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
}
