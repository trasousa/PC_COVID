package COVID.src.GUI;

import COVID.src.Coronita.CoronitaClientAccount;
import COVID.src.Exceptions.AccountException;
import COVID.src.Exceptions.AccountExceptions.MismatchPassException;
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
        JMenuBar mb = new JMenuBar();

        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("NICE_COVID_SERVER");
        frame.add(panel);
        panel.setLayout(null);
        String country[]={"Portugal","Spain","Italy","China"};

        JMenu m1 = new JMenu("ACCOUNT: " + Username);
        JMenuItem m11 = new JMenuItem("LOG OUT");
        m11.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent ev) { System.exit(0); }});
        JMenuItem m12 = new JMenuItem("REMOVE ACCOUNT");
        m12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                JTextPane confpass = new JTextPane();
                JTextPane.getDefaultLocale();
                JOptionPane.showMessageDialog(null, confpass);
                String passconf = confpass.getText();
                try {
                    coronita.removeAccount(Username,passconf);
                    frame.dispose();
                    SignIn sign = new SignIn(EstimateText);
                } catch (MismatchPassException e) {
                    JOptionPane.showMessageDialog(null, "Mismatch Password", "WARNING", JOptionPane.QUESTION_MESSAGE);
                } catch (AccountException e) {
                    e.printStackTrace();
                }
            }
        });
        m1.add(m12);
        m1.add(m11);
        mb.add(m1);

        JMenu m2 = new JMenu("COUNTRY");
        JMenuItem m21 = new JMenuItem("PORTUGAL");
        m21.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent ev) { System.out.println("Alheira"); }});
        JMenuItem m22 = new JMenuItem("SPAIN");
        m22.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent ev) { System.out.println("Olééé"); }});
        JMenuItem m23 = new JMenuItem("ITALY");
        m23.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent ev) { System.out.println("Pasta"); }});
        JMenuItem m24 = new JMenuItem("CHINA");
        m24.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent ev) { System.out.println("5G"); }});
        m2.add(m21);
        m2.add(m22);
        m2.add(m23);
        m2.add(m24);
        mb.add(m2);

        dialog = new JLabel("Select country");
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
                else if(selected.toString().equals("China \uD83C\uDDE8")){
                    System.out.println("5G");
                }
            }
        });
        panel.add(listContries);

        user = new JLabel("Your estimate");
        user.setBounds(20, 20 ,80, 25);
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
        CasesText.setBounds(120, 160, 140, 25);
        panel.add(CasesText);

        button1 = new JButton("Update");
        button1.setBackground(Color.GRAY);
        button1.setBounds(20, 160, 80, 40);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String a =CasesText.getText();
                try {
                    coronita.updateEstimate(a);
                } catch (InvalidNumCases invalidNumCases) {
                    JOptionPane.showMessageDialog(null, "Invalid Number", "WARNING", JOptionPane.QUESTION_MESSAGE);
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
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.setVisible(true);
    }
}
