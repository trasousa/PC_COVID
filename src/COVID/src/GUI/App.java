package COVID.src.GUI;

import COVID.src.Coronita.CoronitaClientAccount;
import COVID.src.Exceptions.AccountException;
import COVID.src.Exceptions.AccountExceptions.MismatchPassException;
import COVID.src.Exceptions.InvalidNumCases;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

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
    private CoronitaClientAccount coronita;
    private String C;
    public int n;
    public Integer EG;
    public Integer EC;
    private Scene scene;


    public App(String Username, int cases, JTextField EstimateGlobal, JTextField EstimateCountry, CoronitaClientAccount coronita, String Country, Scene scene) {
        this.coronita = coronita;
        this.C = Country;
        JFrame frame = new JFrame();
        JPanel container = new JPanel();
        JPanel panel = new JPanel();
        JFXPanel panel2 = new JFXPanel();
        JMenuBar mb = new JMenuBar();
        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    System.exit(0);
                    try {
                        coronita.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (i == JOptionPane.NO_OPTION) {
                    System.out.println("es um mono");
                }
            }
        });
        frame.setTitle("NICE_COVID_SERVER");
        //panel.setSize(200, 300);
        //panel2.setSize(200, 300);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        container.setLayout(new GridLayout(1,2));
        panel.setLayout(null);
        panel2.setLayout(null);
        container.add(panel);
        container.add(panel2);

        JMenu m1 = new JMenu("ACCOUNT: " + Username);
        JMenuItem m11 = new JMenuItem("LOG OUT");
        m11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                coronita.logout();
                frame.dispose();
                LogIn login = new LogIn();
            }
        });
        JMenuItem m12 = new JMenuItem("REMOVE ACCOUNT");
        m12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                JPasswordField confpass = new JPasswordField();
                JPasswordField.getDefaultLocale();
                JOptionPane.showMessageDialog(null, confpass, "REMOVE ACCOUNT", JOptionPane.QUESTION_MESSAGE);
                char[] p = confpass.getPassword();
                String passconf = String.valueOf(p);
                try {
                    coronita.removeAccount(Username, passconf);
                    frame.dispose();
                    SignIn sign = new SignIn(EstimateGlobal, EstimateCountry, scene,coronita);
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

        JMenu m2 = new JMenu("COUNTRY: " + C);
        JMenuItem m21 = new JMenuItem("PORTUGAL \uD83C\uDF77");
        m21.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                n = coronita.setCountry("pt");
                m2.setText("COUNTRY: " + "Portugal");
                setnew(n);
            }
        });
        JMenuItem m22 = new JMenuItem("SPAIN \uD83D\uDC83");
        m22.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                n = coronita.setCountry("es");
                m2.setText("COUNTRY: " + "Spain");
                setnew(n);
            }
        });
        JMenuItem m23 = new JMenuItem("ITALY \uD83C\uDF55");
        m23.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                n = coronita.setCountry("it");
                m2.setText("COUNTRY: " + "Italy");
                setnew(n);
            }
        });
        JMenuItem m24 = new JMenuItem("CHINA \uD83E\uDD87");
        m24.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                n = coronita.setCountry("cn");
                m2.setText("COUNTRY: " + "China");
                setnew(n);
            }
        });
        m2.add(m21);
        m2.add(m22);
        m2.add(m23);
        m2.add(m24);
        mb.add(m2);


        est = new JLabel("Estimatives: ");
        est.setBounds(20, 40, 130, 30);
        est.setFont(new Font("OpenSymbol", Font.BOLD, 14));
        panel.add(est);

        this.EstimateGlobal = EstimateGlobal;
        this.EstimateGlobal.setEditable(false);
        this.EstimateGlobal.setBackground(new Color(228, 102, 56));
        this.EstimateGlobal.setBounds(240, 100, 200, 40);
        panel.add(this.EstimateGlobal);


        this.EstimateCountry = EstimateCountry;
        this.EstimateCountry.setEditable(false);
        this.EstimateCountry.setBackground(new Color(235, 161, 43));
        this.EstimateCountry.setBounds(40, 100, 200, 40);
        panel.add(this.EstimateCountry);

        Cases = new JLabel("Last number of known reported cases : " + cases);
        Cases.setBounds(40, 160, 350, 30);
        Cases.setFont(new Font("OpenSymbol", Font.BOLD, 14));
        panel.add(Cases);

        dialog2 = new JLabel("Keep the number of cases updated");
        dialog2.setBounds(40, 220, 300, 30);
        dialog2.setFont(new Font("OpenSymbol", Font.BOLD, 14));
        panel.add(dialog2);

        CasesText = new JTextField("Insert number", 40);
        CasesText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CasesText.setText("");
            }
        });
        CasesText.setBounds(300, 220, 160, 30);
        panel.add(CasesText);

        button1 = new JButton("UPDATE");
        button1.setBackground(Color.GRAY);
        button1.setBounds(195, 310, 100, 100);
        button1.setBackground(Color.GRAY);
        button1.setFont(new Font("OpenSymbol", Font.BOLD, 14));;
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String a = CasesText.getText();
                    if(!(a.equals("") || a.equals("Insert number") || !(a.matches("\\d+")))) {
                        try {
                            coronita.updateEstimate(a);
                            Cases.setText("Number of known reported cases : " + a);
                        } catch (InvalidNumCases invalidNumCases) {
                            JOptionPane.showMessageDialog(null, "Invalid Number", "WARNING", JOptionPane.QUESTION_MESSAGE);
                            CasesText.setText("Insert number");
                        }
                }
                else CasesText.setText("Invalid input");
            }
        });
        panel.add(button1);
        panel2.setScene(scene);
        frame.add(container);
        frame.setVisible(true);
    }
    public void setnew (int n){
        Cases.setText("Number of known reported cases : " + n);
    }
}


