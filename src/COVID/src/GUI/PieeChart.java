package COVID.src.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;

public class PieeChart{
    Group root;
    PieChart pieChart;

    public PieeChart(){
    }

    public Group getRoot(float eg, float ec) {
        float pop = 100000;
        float cg = eg*pop;
        float cc = ec*(pop/4);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        new PieChart.Data( "Other contries",cg-cc),
        new PieChart.Data("Country slice", cc));
        //Creating a Pie chart
        if (pieChart==null) {
            pieChart = new PieChart();
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
        }
        pieChart.setData(pieChartData);
        //Creating a Group object
        Group root = new Group(pieChart);
        //Adding scene to the stage
        return root;
        }
}