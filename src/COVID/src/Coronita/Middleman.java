package COVID.src.Coronita;

import COVID.src.GUI.PieeChart;
import javafx.scene.Group;
import javafx.scene.Scene;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;


public class Middleman implements Runnable {

    private BufferedReader inServer;
    private static JTextField estimateglobal;
    private static JTextField estimatecountry;
    private Scene scene;
    Group root;
    Thread dealer;
    Bag bag;
    String APPG;
    String APPC;
    float ag;
    float ac;
    Runnable updateEstimateG;
    Runnable updateEstimateC;
    Runnable updatePie;

    public Middleman(Socket socket, Bag bag, JTextField estimateglobal, JTextField estimatecountry, Scene scene) throws IOException {
        this.estimateglobal = estimateglobal;
        this.estimatecountry = estimatecountry;
        this.scene = scene;
        this.inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bag = bag;
        this.APPG = "0.0";
        this.APPC = "0.0";
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
        updatePie = new Runnable() {
            @Override
            public void run() {
                scene.setRoot(root);
            }
        };
    }

    public void start() {
        dealer = new Thread(this);
        dealer.start();

    }

    public void join() {
        try {
            dealer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        String stamp = "";
        while (stamp != null) {
            try {
                stamp = inServer.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] letter = stamp.split("\\s+");
            if (letter[0].equals("est")) {
                APPG = letter[1];
                try {
                    chart();
                    SwingUtilities.invokeAndWait(updateEstimateG);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else if (letter[0].equals("cest")) {
                APPC = letter[1];
                try {
                    chart();
                    SwingUtilities.invokeAndWait(updateEstimateC);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else bag.putLetter(letter);
        }
    }

    public void chart() throws InterruptedException{
        PieeChart piechart;
        ag = Float.parseFloat(APPG);
        ac = Float.parseFloat(APPC);
        piechart = new PieeChart(ag, ac);
        root = piechart.getRoot();
        try {
            SwingUtilities.invokeAndWait(updatePie);
        } catch (InvocationTargetException e) {
            System.out.println("cena que nao sei!!!!");
        }
    }
}

