package COVID.src.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;
import javafx.scene.text.Text;

public class PieeChart{
    Group root;
    PieChart pieChart;
    int i;

    public PieeChart(){
        //Creating a Pie chart
        root = new Group();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data( "Other contries",0.0),
                new PieChart.Data("Country slice", 0.0));
        for(i =0; i<10; i++) {
          try {
              pieChart = new PieChart(pieChartData);
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
              root.getChildren().add(pieChart);
              i=10;
            } catch (Throwable e) {
               System.out.println("errei viu!!");
            }
        }

    }

    public Group getRoot(float eg, float ec) {
        float pop = 100000;
        float cg = eg*pop;
        float cc = ec*(pop/4);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data( "Other contries",cg-cc),
                new PieChart.Data("Country slice", cc));
        if(pieChart!=null){
            pieChart.setData(pieChartData);
            System.out.println("estou aqui contigo");
        }
        else{
            Text error = new Text("errei");
            root.getChildren().add(error);
        }
        return root;
        }
}