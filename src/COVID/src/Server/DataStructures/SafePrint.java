package COVID.src.Server.DataStructures;

import java.io.PrintWriter;
import java.util.concurrent.locks.ReentrantLock;

public class SafePrint{
    PrintWriter out;
    ReentrantLock lock;
    public SafePrint(PrintWriter out){
        this.out = out;
        lock = new ReentrantLock();
    }

    public void println(String message){
        lock.lock();
        out.println(message);
        out.flush();
        lock.unlock();
    }
}
