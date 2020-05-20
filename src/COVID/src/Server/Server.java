package COVID.src.Server;

import COVID.src.Coronita.Interface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import COVID.src.Server.Account;

public class Server {

    public static void main(String[] args) {
        Accounts accounts = new Accounts();
        Estimate estimate = new Estimate(accounts);
        ServerSocket sSock = null;
        try {
            sSock = new ServerSocket( 	60833);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
            try {
                Socket cliente = sSock.accept();
                Thread worker = new Thread(new Worker(cliente,estimate,accounts));
                worker.start();
                System.out.println("Chegou!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
