package Coronita;

import GUI.PieeChart;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;


public class Middleman implements Runnable {

    private BufferedReader inServer;
    private static JTextField estimateglobal;
    private static JTextField estimatecountry;
    private Scene scene;
    PieeChart piechart;
    Thread dealer;
    Bag bag;
    String APPG;
    String APPC;
    float ag;
    float ac;
    Runnable updateEstimateG;
    Runnable updateEstimateC;
    Runnable updatePie;

    public Middleman(BufferedReader inServer, Bag bag, JTextField estimateglobal, JTextField estimatecountry, Scene scene) throws IOException {
        this.estimateglobal = estimateglobal;
        this.estimatecountry = estimatecountry;
        this.scene = scene;
        this.piechart = new PieeChart();
        this.inServer = inServer;
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
                float eg;
                float ec;
                Group root;
                eg= Float.parseFloat(APPG);
                ec = Float.parseFloat(APPC);
                root = piechart.getRoot(eg,ec);
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

            if (letter[0].equals("cest")) {
                APPC = letter[1];
                SwingUtilities.invokeLater(updateEstimateC);
            }

            else if (letter[0].equals("est")) {
                APPG = letter[1];
                SwingUtilities.invokeLater(updateEstimateG);
                try {
                    Platform.runLater(updatePie);
                }
                catch(IllegalStateException e){
                    System.out.println("Graph loading");
                }
            }
            else bag.putLetter(letter);
        }
    }
}

