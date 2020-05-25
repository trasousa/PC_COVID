package COVID.src.Server.DataStructures;

import java.io.PrintWriter;
import java.util.concurrent.locks.ReentrantLock;

public class SafePrint{
    PrintWriter out;
    ReentrantLock lock;
    public SafePrint(PrintWriter out){
        this.out = out;
    }
    public void println(String message){
        out.println(message);
        out.flush();
    }
}
