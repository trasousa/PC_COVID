package COVID.src.Server;

import java.io.PrintWriter;

public class Writer implements Runnable{
    Thread writer;
    boolean flag;
    PrintWriter out;
    Estimate estimate;
    String idCliente;
    public Writer(PrintWriter out, Estimate estimate){
        this.out= out;
        this.estimate = estimate;
        flag = true;
    }

    public void start(String idCliente){
        this.idCliente = idCliente;
        writer = new Thread(this);
        writer.start();
    }

    public Thread stop(){
        if(writer != null) {
            flag = false;
            try {
                writer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void run() {
        while(flag){
            try {
                float newEstimate = estimate.getEstimate(idCliente);
                out.println("est " + newEstimate);
                out.flush();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
