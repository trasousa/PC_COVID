package COVID.src.GUI;

import COVID.src.Coronita.CoronitaClientAccount;
import COVID.src.Exceptions.AccountException;
import COVID.src.Exceptions.AccountExceptions.MismatchPassException;
import COVID.src.Exceptions.InvalidNumCases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


public class App extends JFrame {
    private static JLabel est;
    private static JLabel dialog2;
    private static JLabel Cases;
    private static JTextField CasesText;
    private static JTextField EstimateGlobal;
    private static JTextField EstimateCountry;
    private static JButton button1;
    private static JButton button2;
    private CoronitaClientAccount coronita;


    public App(String Username, int cases, JTextField EstimateGlobal, JTextField EstimateCountry, CoronitaClientAccount coronita) {

        this.coronita = coronita;
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JMenuBar mb = new JMenuBar();

        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                int i=JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING",    JOptionPane.YES_NO_OPTION);
                if(i== JOptionPane.YES_OPTION){
                    System.exit(0);
                    try {
                        coronita.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else if (i== JOptionPane.NO_OPTION){
                    System.out.println("es um mono");
                }
            }
        });
        frame.setTitle("NICE_COVID_SERVER");
        frame.add(panel);
        panel.setLayout(null);

        JMenu m1 = new JMenu("ACCOUNT: " + Username);
        JMenuItem m11 = new JMenuItem("LOG OUT");
        m11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                coronita.logout();
                frame.dispose();
                LogIn login = new LogIn();
            }});
        JMenuItem m12 = new JMenuItem("REMOVE ACCOUNT");
        m12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                JPasswordField confpass = new JPasswordField();
                JPasswordField.getDefaultLocale();
                JOptionPane.showMessageDialog(null, confpass);
                char[] p = confpass.getPassword();
                String passconf = String.valueOf(p);
                try {
                    coronita.removeAccount(Username,passconf);
                    frame.dispose();
                    SignIn sign = new SignIn(EstimateGlobal,EstimateCountry);
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
        JMenuItem m21 = new JMenuItem("PORTUGAL \uD83C\uDDF5\uD83C\uDDF9");
        m21.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent ev) { coronita.setCountry("pt");}});
        JMenuItem m22 = new JMenuItem("SPAIN \uD83C\uDDEA\uD83C\uDDE6");
        m22.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent ev) { coronita.setCountry("es");}});
        JMenuItem m23 = new JMenuItem("ITALY \uD83C\uDDEE\uD83C\uDDF9");
        m23.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent ev) { coronita.setCountry("it"); }});
        JMenuItem m24 = new JMenuItem("CHINA \uD83C\uDDE8\uD83C\uDDF3");
        m24.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent ev) { coronita.setCountry("cn"); }});
        m2.add(m21);
        m2.add(m22);
        m2.add(m23);
        m2.add(m24);
        mb.add(m2);


        est = new JLabel("Estimatives: ");
        est.setBounds(20, 20 ,80, 25);
        panel.add(est);

        this.EstimateGlobal = EstimateGlobal;
        this.EstimateGlobal.setEditable(false);
        this.EstimateGlobal.setBackground(Color.GRAY);
        this.EstimateGlobal.setBounds(30, 45, 80,40 );
        panel.add(this.EstimateGlobal);


        this.EstimateCountry = EstimateCountry;
        this.EstimateCountry.setEditable(false);
        this.EstimateCountry.setBackground(Color.GRAY);
        this.EstimateCountry.setBounds(150, 45, 80,40 );
        panel.add(this.EstimateCountry);

        Cases = new JLabel("Number of known reported cases : " + cases);
        Cases.setBounds(20, 90, 300, 25);
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

        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.setVisible(true);
    }
}
