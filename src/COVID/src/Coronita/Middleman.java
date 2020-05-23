package COVID.src.Coronita;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;


public class Middleman implements Runnable{

    private BufferedReader inServer;
    private static JTextField estimateText;
    Thread dealer;
    Boolean flag;
    Bag bag;
    String APP;
    Runnable updateEstimate;

    public Middleman(Socket socket, Bag bag, JTextField estimateText) throws IOException {
        this.estimateText = estimateText;
        this.inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bag = bag;
        this.APP = null;
        flag = true;
        updateEstimate = new Runnable() {
            @Override
            public void run() {
                estimateText.setText("Global estimated case " + APP);
            }
        };
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
        while (flag){
            try {
                stamp= inServer.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] letter = stamp.split("\\s+");
            if(letter[0].equals("est")){
                APP = letter[1];
                try {
                    SwingUtilities.invokeAndWait(updateEstimate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            else bag.putLetter(letter);
        }
    }

}

