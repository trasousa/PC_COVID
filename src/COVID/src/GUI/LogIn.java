package COVID.src.GUI;

import COVID.src.Coronita.CoronitaClientAccount;
import COVID.src.Exceptions.AccountExceptions.InvalidAccount;
import COVID.src.Exceptions.AccountExceptions.MismatchPassException;
import javafx.scene.Group;
import javafx.scene.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class LogIn extends JFrame {

        private static JLabel UserLabel;
        private static JTextField UserText;
        private static JTextField EstimateGlobal;
        private static JTextField EstimateCountry;
        private static JLabel PasswordLabel;
        private static Scene scene;
        private static JPasswordField PassText;
        private static JLabel CountryLabel;
        private static JComboBox country;
        private static JButton button;
        private static JButton button2;
        private CoronitaClientAccount coronita;
        private String s;
        private String b;
        private Object selectedItem;
        private Group root;

    public LogIn(){

        try {
            this.EstimateGlobal = new JTextField("Global ");
            this.EstimateCountry = new JTextField("Country ");
            this.root = new Group();
            this.scene = new Scene(root , 200,300);
            this.coronita = new CoronitaClientAccount(EstimateGlobal,EstimateCountry,scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setSize(350,200);
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
                    System.out.println("quit");
                }
            }
        });
        frame.setTitle("NICE_COVID_SERVER");
        frame.add(panel);
        panel.setLayout(null);

        UserLabel = new JLabel("User");
        UserLabel.setBounds(10, 20, 80, 25);
        panel.add(UserLabel);

        UserText = new JTextField ();
        UserText.setBounds(100, 20 ,165,25);
        panel.add(UserText);

        PasswordLabel = new JLabel("Password");
        PasswordLabel.setBounds(10, 60, 80, 25);
        panel.add(PasswordLabel);

        PassText = new JPasswordField();
        PassText.setBounds(100, 60 ,165,25);
        panel.add(PassText);

        CountryLabel = new JLabel("Select Country");
        CountryLabel.setBounds(10, 100, 120, 25);
        panel.add(CountryLabel);

        String countryList[] = {"Portugal \uD83C\uDF77","Spain \uD83D\uDC83","Italy \uD83C\uDF55","China \uD83E\uDD87"};
        country = new JComboBox(countryList);
        country.setBounds(120,100,150,25);
        country.setFont(new Font("OpenSymbol", Font.ITALIC, 14));
        s = ("pt");
        b = ("Portugal");
        country.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Object selction = country.getItemAt(country.getSelectedIndex());
                if(selction.equals("Portugal \uD83C\uDF77")){
                    s =("pt");
                    b = "Portugal";
                }
                if(selction.equals("Spain \uD83D\uDC83")){
                    s = ("es");
                    b = "Spain";
                }
                if(selction.equals("Italy \uD83C\uDF55")){
                    s =("it");
                    b = "Italy";
                }
                if(selction.equals("China \uD83E\uDD87")){
                    s = ("cn");
                    b = "China";
                }
            }

        });
        panel.add(country);

        button = new JButton("Login");
        button.setBounds(50, 140 ,80, 25);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user = UserText.getText();
                char[] pass = PassText.getPassword();
                String password = String.valueOf(pass);
                try {
                    coronita.authenticate(user, password);
                    int a =  coronita.setCountry(s);
                    frame.dispose();
                    App app = new App(user,a, EstimateGlobal, EstimateCountry, coronita, b,scene);
                    System.out.println(a);
                } catch (InvalidAccount e) {
                    JOptionPane.showMessageDialog(null, "Invalid Account", "WARNING", JOptionPane.WARNING_MESSAGE);
                    System.out.println("Invalid Account");
                    UserText.setText("");
                    PassText.setText("");
                } catch (MismatchPassException e) {
                    JOptionPane.showMessageDialog(null, "Invalid Password", "WARNING", JOptionPane.WARNING_MESSAGE);
                    System.out.println("Invalid Password");
                    PassText.setText("");
                }
            }
        });
        panel.add(button);
        button2 = new JButton("Sing in");
        button2.setBounds(150, 140 ,80, 25);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
                SignIn sign = new SignIn(EstimateGlobal,EstimateCountry,scene,coronita);
            }
        });
        panel.add(button2);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        new LogIn();
    }

}
