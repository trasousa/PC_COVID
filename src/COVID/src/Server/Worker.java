package COVID.src.Server;

import COVID.src.Coronita.Interface;
import COVID.src.Exceptions.InvalidAcount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Worker implements Runnable, Interface {
    BufferedReader in;
    PrintWriter out;
    Writer writer;
    public Worker(Socket client){
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream());
            writer = new Writer(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {

    }

    @Override
    public void registarCliente(String id, String passwd) {

    }

    @Override
    public void removerCliente(String id) throws InvalidAcount {

    }

    @Override
    public void autenticar(String id, String passwd) throws InvalidAcount {

    }

    @Override
    public void atualizarEstimativa(int cases) {

    }
}
