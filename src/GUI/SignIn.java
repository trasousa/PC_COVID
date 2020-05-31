package GUI;

import Coronita.CoronitaClientAccount;
import Exceptions.AccountExceptions.InvalidAccount;
import Exceptions.AccountExceptions.InvalidUsername;
import Exceptions.AccountExceptions.MismatchPassException;
import Exceptions.PasswordException;
import javafx.scene.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;

public class SignIn extends JFrame{

    private static JLabel NameLabel;
    private static JTextField NameText;
    private static JTextField EstimateGlobal;
    private static JTextField EstimateCountry;
    private static Scene scene;
    private static JLabel UserLabel;
    private static JTextField UserText;
    private static JLabel PasswordLabel;
    private static JPasswordField PassText;
    private static JLabel PasswordLabel2;
    private static JPasswordField PassText2;
    private static JLabel CountryLabel;
    private static JComboBox country;
    private String s;
    private String b;
    private static JButton button;
    private CoronitaClientAccount coronita;


    public SignIn(JTextField EstimateGlobal, JTextField EstimateCountry, Scene scene, CoronitaClientAccount coronita) {
        this.EstimateGlobal = EstimateGlobal;
        this.EstimateCountry = EstimateCountry;
        this.scene = scene;
        this.coronita = coronita;
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setSize(350, 400 );
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


        UserLabel = new JLabel("User");
        UserLabel.setBounds(10, 20, 80, 25);
        panel.add(UserLabel);

        UserText = new JTextField();
        UserText.setBounds(100, 20, 165, 25);
        panel.add(UserText);

        PasswordLabel = new JLabel("Password");
        PasswordLabel.setBounds(10, 60, 80, 25);
        panel.add(PasswordLabel);

        PassText = new JPasswordField();
        PassText.setBounds(100, 60, 165, 25);
        panel.add(PassText);

        PasswordLabel2 = new JLabel("Conf. Password");
        PasswordLabel2.setBounds(10, 100, 80, 25);
        panel.add(PasswordLabel2);

        PassText2 = new JPasswordField();
        PassText2.setBounds(100, 100, 165, 25);
        panel.add(PassText2);

        CountryLabel = new JLabel("Select Country");
        CountryLabel.setBounds(10, 140, 120, 25);
        panel.add(CountryLabel);

        String countryList[] = {"Portugal \uD83C\uDF77","Spain \uD83D\uDC83","Italy \uD83C\uDF55","China \uD83E\uDD87"};
        country = new JComboBox(countryList);
        country.setFont(new Font("OpenSymbol", Font.ITALIC, 14));
        country.setBounds(120,140,150,25);
        s = "pt";
        b = "Portugal";
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


        button = new JButton("Sign in");
        button.setBounds(100, 200, 80, 40);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user = UserText.getText();
                char[] pass1 = PassText.getPassword();
                String password = String.valueOf(pass1);
                char[] pass2 = PassText2.getPassword();
                String password2 = String.valueOf(pass2);
                try {
                    coronita.chekUsername(user);
                    coronita.registerAccount(user, password, password2);
                    coronita.authenticate(user,password);
                    int a = coronita.setCountry(s);
                    frame.dispose();
                    App app = new App(user,a, EstimateGlobal,EstimateCountry, coronita,b,scene);
                    System.out.println("Successfully Registered");
                } catch (MismatchPassException e) {
                    JOptionPane.showMessageDialog(null,"Mismatch Password", "WARNING", JOptionPane.WARNING_MESSAGE);
                    System.out.println("Mismatch Password");
                    PassText.setText("");
                    PassText2.setText("");
                } catch (PasswordException ActionEvent) {
                    JOptionPane.showMessageDialog(null,"Invalid Password", "WARNING", JOptionPane.WARNING_MESSAGE);
                    System.out.println("Invalid Password");
                    PassText.setText("");
                    PassText2.setText("");
                } catch (InvalidUsername invalidUsername) {
                    JOptionPane.showMessageDialog(null,"Invalid Username", "WARNING", JOptionPane.WARNING_MESSAGE);
                    System.out.println("Invalid Username");
                    Random rand = new Random();
                    int suggestion = rand.nextInt(1000);
                    UserText.setText(user + suggestion);
                }catch (InvalidAccount e) {
                    System.out.println("Invalid Acount");
                }
            }
        });

        panel.add(button);
        frame.setVisible(true);
    }
}