package Server;

import Server.DataStructures.Estimates;
import Server.DataStructures.Pair;
import Server.DataStructures.SafePrint;

public class Writer implements Runnable{
    Thread writer;
    Estimates estimates;
    boolean flag;
    SafePrint out;
    String country;
    String idCliente;
    public Writer(SafePrint out,Estimates estimates){
        this.out= out;
        this.estimates = estimates;
    }

    public void start(String idCliente,String country){
        flag = true;
        this.idCliente = idCliente;
        this.country = country;
        writer = new Thread(this);
        writer.start();
    }
    public void stop(){
        if(writer != null){
            flag = false;
        }
    }

    public void join(){
        try {
            writer.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(flag){
            estimates.waitUpdate(idCliente);
            if(flag) {
                Pair<Float, Float> newEstimate = estimates.getEstimate(idCliente, country);
                if (newEstimate.getSnd() != -1) {
                    out.println("cest " + newEstimate.getSnd());
                }
                out.println("est " + newEstimate.getFst());
            }
        }
    }
}
