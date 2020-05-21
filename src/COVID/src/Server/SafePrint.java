package COVID.src.Server;

import java.io.PrintWriter;

public class SafePrint{
    PrintWriter out;

    public SafePrint(PrintWriter out){
        this.out = out;
    }
    public void println(String message){
        out.println(message);
        out.flush();
    }
}
