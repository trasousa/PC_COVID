package COVID.src.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        ServerSocket sSock = null;
        try {
            sSock = new ServerSocket( 	60833);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
        try {
            Socket cliente = sSock.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter out = new PrintWriter(cliente.getOutputStream());
            Writer writer = new Writer(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
}
