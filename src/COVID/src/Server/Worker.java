package COVID.src.Server;

import COVID.src.Coronita.Interface;
import COVID.src.Exceptions.InvalidAcount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Worker implements Runnable, Interface {
    Socket client;
    BufferedReader in;
    PrintWriter out;
    public Worker(Socket client){
        try {
            client = client;
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        Writer writer = new Writer(out);
        String read = null;
        try {
            read = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(!(read.equals("quit")) || read != null){
            String[] readParts = read.split("//s+",2);
            String commmand = readParts[0];
            String[] args;
            switch (commmand){
                case "cr":
                    args = readParts[1].split("//s+");
                    CreateAcount(args[1],args[2]);
                case "lg":
                    args = readParts[1].split("//s+");
                    try {
                        authenticate(args[0],args[1]);
                    } catch (InvalidAcount InvalidAccount) {
                        out.println("Account exists");
                        out.flush();
                    }
                case "up":
                    args = readParts[1].split("//s+");
                    updateEstimate(Integer.parseInt(args[0]));
                case "rm":
                    args = readParts[1].split("//s+");
                    try {
                        removeClient(args[0]);//,args[1]);
                    } catch (InvalidAcount invalidAcount) {
                        out.println("Account exists");
                        out.flush();
                    }
            }
        }
        (writer.stop()).join(); //devolve thread
        try {
            client.shutdownInput();
            client.shutdownOutput();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double CreateAcount(String Username, String pass) {

    }

    @Override
    public void registerClient(String id, String passwd) {

    }

    @Override
    public void removeClient(String id) throws InvalidAcount {

    }

    @Override
    public void authenticate(String id, String passwd) throws InvalidAcount {

    }

    @Override
    public void updateEstimate(int cases) {

    }
}
