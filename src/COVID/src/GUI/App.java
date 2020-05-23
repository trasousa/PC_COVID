package COVID.src.GUI;

import COVID.src.Coronita.CoronitaClientAccount;
import COVID.src.Exceptions.InvalidNumCases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/* import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
  */

public class App extends JFrame {
    private static JLabel user;
    private static JLabel dialog;
    private static JLabel dialog2;
    private static JLabel Cases;
    private static JTextField CasesText;
    private static JTextField EstimateText;
    private static JLabel success;
    private static JButton button1;
    private static JButton button2;
    private static JComboBox listContries;
    private CoronitaClientAccount coronita;


    public App(String Username, int cases, JTextField EstimateText, CoronitaClientAccount coronita) {
        this.coronita = coronita;
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("NICE_COVID_SERVER");
        frame.add(panel);
        panel.setLayout(null);
        String country[]={"Portugal","Spain","Italy","China"};

        dialog = new JLabel("Select your country");
        dialog.setBounds(300,20,200,25);
        panel.add(dialog);

        listContries =new JComboBox(country);
        listContries.setBounds(300, 60,90,20);
        listContries.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                listContries = (JComboBox) event.getSource();
                Object selected = listContries.getSelectedItem();
                if(selected.toString().equals("Portugal")){
                    System.out.println("Alheira");
                }
                else if(selected.toString().equals("Spain")){
                    System.out.println("Olééé");
                }
                else if(selected.toString().equals("Italy")){
                    System.out.println("Pasta");
                }
                else if(selected.toString().equals("China")){
                    System.out.println("5G");
                }
            }
        });
        panel.add(listContries);

        user = new JLabel("Loged into Account: " + Username);
        user.setBounds(20, 20 ,200, 25);
        panel.add(user);


        this.EstimateText = EstimateText;
        this.EstimateText.setEditable(false);
        this.EstimateText.setBackground(Color.GRAY);
        this.EstimateText.setBounds(20, 40, 250,40 );
        panel.add(this.EstimateText);

        Cases = new JLabel("Number of known reported cases : " + cases);
        Cases.setBounds(20, 80, 300, 25);
        panel.add(Cases);

        dialog2 = new JLabel("Keep the number of cases updated");
        dialog2.setBounds(20,120,300,25);
        panel.add(dialog2);

        CasesText = new JTextField("Insert number",40);
        CasesText.addMouseListener(new MouseAdapter(){@Override public void mouseClicked(MouseEvent e){ CasesText.setText(""); }});
        CasesText.setBounds(150, 160, 200, 25);
        panel.add(CasesText);

        button1 = new JButton("Update");
        button1.setBounds(20, 160, 80, 40);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String a =CasesText.getText();
                try {
                    coronita.updateEstimate(a);
                } catch (InvalidNumCases invalidNumCases) {
                    JOptionPane.showMessageDialog(null, "Invalid Number", "WARNING", JOptionPane.WARNING_MESSAGE);
                    CasesText.setText("Insert number");
                }
            }
        });
        panel.add(button1);
/*
        DefaultXYDataset ds = new DefaultXYDataset();
        double[][] data = { {0.1, 0.2, 0.3}, {1, 2, 3} };
        ds.addSeries("series1", data);
        XYDataset ds = createDataset();
        JFreeChart chart = ChartFactory.createXYLineChart("Test Chart",
                "x", "y", ds, PlotOrientation.VERTICAL, true, true,
                false);

        ChartPanel cp = new ChartPanel(chart);

        frame.getContentPane().add(cp);
*/
        frame.setVisible(true);
    }
}
