package COVID.src.Coronita;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Middleman implements Runnable{

    private BufferedReader inServer;
    Thread dealer;
    Boolean flag;
    Bag bag;

    public Middleman(String host, int port, Socket socket) throws IOException {
        this.inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        flag = true;
    }
    public void start(){
        dealer = new Thread(this);
        dealer.start();

    }
    public void stop(){
        if(dealer !=null){
            flag = false;
            try {
                dealer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void run() {
        String stamp = null;
        int number;
        while (flag){
            try {
                stamp= inServer.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] letter = stamp.split("\\s+");
            if(letter[0].equals("est")){
                number = Integer.parseInt(letter[1]);
                //cena do GUI
            }
            else bag.putLetter(letter);

        }
        }

}

