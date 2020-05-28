package COVID.src.Server;

import COVID.src.Server.DataStructures.Accounts;
import COVID.src.Server.DataStructures.Estimates;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        Accounts accounts = new Accounts();
        Estimates estimates = new Estimates();
        ServerSocket sSock = null;
        try {
            sSock = new ServerSocket(60833);
        } catch (IOException e) {
            System.out.println("Error opening socket, restart server");
            e.printStackTrace();
        }
        while(true){
            try {
                Socket cliente = sSock.accept();
                System.out.println("New client.\nAddress: " + cliente.getInetAddress());
                Thread worker = new Thread(new Worker(cliente,estimates,accounts));
                worker.start();
            } catch (IOException e){
                System.out.println("Server has failed to acceot a connection");
            }
        }
    }
}
