package COVID.src.GUI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;

public class FXInSwing extends JFrame{

    JFXPanel panel;
    Scene scene;
    StackPane stack;
    Text hello;
    Stage stage;
    boolean wait = true;

    public FXInSwing(){
        panel = new JFXPanel();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data("Iphone 5S", 13),
                        new PieChart.Data("Samsung Grand", 25),
                        new PieChart.Data("MOTO G", 10),
                        new PieChart.Data("Nokia Lumia", 22));

                //Creating a Pie chart
                PieChart pieChart = new PieChart(pieChartData);

                //Setting the title of the Pie chart
                pieChart.setTitle("Mobile Sales");

                //setting the direction to arrange the data
                pieChart.setClockwise(true);

                //Setting the length of the label line
                pieChart.setLabelLineLength(50);

                //Setting the labels of the pie chart visible
                pieChart.setLabelsVisible(true);

                //Setting the start angle of the pie chart
                pieChart.setStartAngle(180);

                //Creating a Group object
                Group root = new Group(pieChart);

                //Creating a scene object
                Scene scene = new Scene(root, 600, 400);

                //Setting title to the Stage
                stage.setTitle("Pie chart");

                //Adding scene to the stage
                stage.setScene(scene);

                //Displaying the contents of the stage
                stage.show();
            }
        });
        this.getContentPane().add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 300);
        this.setVisible(true);
    }
}