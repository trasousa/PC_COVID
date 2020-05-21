package COVID.src.terminal;

import COVID.src.Coronita.CoronitaServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class terminalClient{
    public static void main(String args[]){
        String host = "127.0.0.1";
        int port = 60833;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            CoronitaServer stub = new CoronitaServer(host,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
