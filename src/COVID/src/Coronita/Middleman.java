package COVID.src.Coronita;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;


public class Middleman implements Runnable{

    private BufferedReader inServer;
    private static JTextField estimateglobal;
    private static JTextField estimatecountry;
    Thread dealer;
    Bag bag;
    String APPG;
    String APPC;
    Runnable updateEstimateG;
    Runnable updateEstimateC;

    public Middleman(Socket socket, Bag bag, JTextField estimateglobal, JTextField estimatecountry) throws IOException {
        this.estimateglobal = estimateglobal;
        this.estimatecountry = estimatecountry;
        this.inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bag = bag;
        this.APPG = null;
        this.APPC = null;
        updateEstimateG = new Runnable() {
            @Override
            public void run() {
                estimateglobal.setText("Global " + APPG);
            }
        };
        updateEstimateC = new Runnable() {
            @Override
            public void run() {
                estimatecountry.setText("Country " + APPC);
            }
        };
    }
    public void start(){
        dealer = new Thread(this);
        dealer.start();

    }
    public void join(){
        try {
            dealer.join();
        } catch (InterruptedException e) {
                e.printStackTrace();
        }

    }
    @Override
    public void run() {
        String stamp = "";
        while (stamp!=null){
            try {
                stamp= inServer.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] letter = stamp.split("\\s+");
            if(letter[0].equals("est")){
                APPG = letter[1];
                try {
                    SwingUtilities.invokeAndWait(updateEstimateG);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            else if(letter[0].equals("cest")){
                APPC = letter[1];
                try {
                    SwingUtilities.invokeAndWait(updateEstimateC);
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

