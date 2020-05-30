package COVID.src.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;

public class PieeChart{
    Group root;
    float eg;
    float ec;

    public PieeChart(float eg, float ec){
        this.ec = ec;
        this.eg = eg;
    }

    public Group getRoot() {
        float pop = 100000;
        float cg = eg*pop;
        float cc = ec*(pop/4);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        new PieChart.Data( "Other contries",cg-cc),
        new PieChart.Data("Country slice", cc));
        //Creating a Pie chart
        PieChart pieChart = new PieChart(pieChartData);
        //Setting the title of the Pie chart
        pieChart.setTitle("Global Pandemic");
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
        //Adding scene to the stage
        return root;
        }
}