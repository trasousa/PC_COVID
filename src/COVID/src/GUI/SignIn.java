package COVID.src.GUI;

import COVID.src.Coronita.CoronitaClientAccount;
import COVID.src.Exceptions.AccountException;
import COVID.src.Exceptions.AccountExceptions.InvalidAccount;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;
import COVID.src.Exceptions.PasswordException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

public class SignIn extends JFrame{

    private static JLabel NameLabel;
    private static JTextField NameText;
    private static JLabel UserLabel;
    private static JTextField UserText;
    private static JLabel PasswordLabel;
    private static JPasswordField PassText;
    private static JLabel PasswordLabel2;
    private static JPasswordField PassText2;
    private static JLabel success;
    private static JButton button;
    private CoronitaClientAccount coronita;


    public SignIn() {

        try {
            this.coronita = new CoronitaClientAccount();
        } catch (IOException e) {
            e.printStackTrace();
        }


        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setSize(350, 400 );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        PasswordLabel2 = new JLabel("Confirm Password");
        PasswordLabel2.setBounds(10, 100, 80, 25);
        panel.add(PasswordLabel2);

        PassText2 = new JPasswordField();
        PassText2.setBounds(100, 100, 165, 25);
        panel.add(PassText2);

        button = new JButton("Sign up");
        button.setBounds(10, 140, 80, 25);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user = UserText.getText();
                try {
                    coronita.chekUsername(user);
                } catch (InvalidUsername invalidUsername) {
                    JOptionPane.showMessageDialog(null,"Invalid Username", "WARNING", JOptionPane.WARNING_MESSAGE);
                    System.out.println("Invalid Username");
                    byte[] array = new byte[3];
                    new Random().nextBytes(array);
                    String suggestion = new String(array, Charset.forName("UTF-8"));
                    UserText.setText(user + suggestion);
                }
                char[] pass1 = PassText.getPassword();
                String password = String.valueOf(pass1);
                char[] pass2 = PassText2.getPassword();
                String password2 = String.valueOf(pass2);
                try {
                    coronita.registerAccount(user, password, password2);
                    frame.dispose();
                    App app = (new App());
                    System.out.println("Successfully Registered");
                } catch (AccountException e) {
                    JOptionPane.showMessageDialog(null,"Mismatch Password", "WARNING", JOptionPane.WARNING_MESSAGE);
                    System.out.println("Mismatch Password");
                    PassText.setText("");
                    PassText2.setText("");

                } catch (PasswordException ActionEvent) {
                    JOptionPane.showMessageDialog(null,"Invalid Password", "WARNING", JOptionPane.WARNING_MESSAGE);
                    System.out.println("Invalid Password");
                    PassText.setText("");
                    PassText2.setText("");
                }
            }
        });

        panel.add(button);
        frame.setVisible(true);
    }
}