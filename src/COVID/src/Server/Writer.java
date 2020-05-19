package COVID.src.Server;

import java.io.PrintWriter;

public class Writer implements Runnable{
    Thread writer;
    boolean flag;
    PrintWriter out;
    Estimate estimate;
    String idCliente;
    public Writer(PrintWriter out, Estimate estimate, String idCliente){
        this.out= out;
        this.estimate = estimate;
        this.idCliente = idCliente;
        flag = true;
    }

    public void start(){
        writer = new Thread(this);
        writer.start();
    }

    public Thread stop(){
        flag = false;
        return writer;
    }

    @Override
    public void run() {
        while(flag){
            try {
                float newEstimate = estimate.getEstimate(idCliente);
                out.println("Numero de casos" + newEstimate);
                out.flush();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
