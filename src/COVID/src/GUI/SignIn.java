package COVID.src.GUI;

import COVID.src.Coronita.CoronitaClientAccount;
import COVID.src.Exceptions.AccountExceptions.InvalidAccount;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;
import COVID.src.Exceptions.AccountExceptions.MismatchPassException;
import COVID.src.Exceptions.PasswordException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

public class SignIn extends JFrame{

    private static JLabel NameLabel;
    private static JTextField NameText;
    private static JTextField EstimateGlobal;
    private static JTextField EstimateCountry;
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


    public SignIn(JTextField EstimateGlobal, JTextField EstimateCountry) {
        this.EstimateGlobal = EstimateGlobal;
        this.EstimateCountry = EstimateCountry;
        try {
            this.coronita = new CoronitaClientAccount(EstimateGlobal,EstimateCountry);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        String countryList[] = {"Portugal \uD83C\uDDF5\uD83C\uDDF9","Spain \uD83C\uDDEA\uD83C\uDDE6","Italy \uD83C\uDDEE\uD83C\uDDF9","China \uD83C\uDDE8\uD83C\uDDF3"};
        country = new JComboBox(countryList);
        country.setFont(new Font("OpenSymbol", Font.ITALIC, 12));
        country.setBounds(120,140,150,25);
        country.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Object selction = country.getItemAt(country.getSelectedIndex());
                if(selction.equals("Portugal \uD83C\uDDF5\uD83C\uDDF9")){
                    s =("pt");
                    String b = "Portugal";
                }
                if(selction.equals("Spain \uD83C\uDDEA\uD83C\uDDE6")){
                    s = ("es");
                    String b = "Spain";
                }
                if(selction.equals("Italy \uD83C\uDDEE\uD83C\uDDF9")){
                    s =("it");
                    String b = "Italy";
                }
                if(selction.equals("China \uD83C\uDDE8\uD83C\uDDF3")){
                    s = ("cn");
                    String b = "China";
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
                    App app = new App(user,a, EstimateGlobal,EstimateCountry, coronita,b);
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
                    byte[] array = new byte[3];
                    new Random().nextBytes(array);
                    String suggestion = new String(array, Charset.forName("UTF-8"));
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