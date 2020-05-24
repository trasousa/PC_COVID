package COVID.src.Server;

import java.io.PrintWriter;

public class Writer implements Runnable{
    Thread writer;
    boolean flag;
    SafePrint out;
    Estimate estimate;
    String idCliente;
    public Writer(SafePrint out){
        this.out= out;
        flag = true;
    }

    public void start(String idCliente,Estimate estimate){
        this.idCliente = idCliente;
        this.estimate = estimate;
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(flag){
            try {
                float newEstimate = estimate.getEstimate(idCliente);
                out.println("est " + newEstimate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
