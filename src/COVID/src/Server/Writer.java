package COVID.src.Server;

import java.io.PrintWriter;

public class Writer implements Runnable{
    Thread writer;
    boolean flag;
    PrintWriter out;
    public Writer(PrintWriter out){
        this.out= out;
        flag = true;
    }

    @Override
    public void run() {
        while(flag){
            float
        }
    }
}
