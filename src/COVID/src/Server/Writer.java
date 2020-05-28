package COVID.src.Server;

import COVID.src.Server.DataStructures.Estimate;
import COVID.src.Server.DataStructures.Estimates;
import COVID.src.Server.DataStructures.Pair;
import COVID.src.Server.DataStructures.SafePrint;

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
                out.println("est " + newEstimate.getFst());
                if (newEstimate.getSnd() != -1) {
                    out.println("cest " + newEstimate.getSnd());
                }
            }
        }
    }
}
