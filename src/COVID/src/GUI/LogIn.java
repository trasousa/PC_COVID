package COVID.src.GUI;

import COVID.src.Coronita.CoronitaClientAccount;
import COVID.src.Coronita.CoronitaServer;
import COVID.src.Exceptions.AccountExceptions.InvalidAccount;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;

import javax.security.auth.login.AccountException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LogIn extends JFrame {

        private static JLabel UserLabel;
        private static JTextField UserText;
        private static JLabel PasswordLabel;
        private static JPasswordField PassText;
        private static JLabel success;
        private static JButton button;
        private static JButton button2;
        private CoronitaClientAccount coronita;

    public LogIn(){
        try {
            this.coronita = new CoronitaClientAccount();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setSize(350,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        PasswordLabel.setBounds(10, 50, 80, 25);
        panel.add(PasswordLabel);

        PassText = new JPasswordField();
        PassText.setBounds(100, 50 ,165,25);
        panel.add(PassText);

        success = new JLabel("");
        success.setBounds(10,110,300,25);
        panel.add(success);

        button = new JButton("Login");
        button.setBounds(10, 80 ,80, 25);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user = UserText.getText();
                try {
                    coronita.chekUsername(user);
                } catch (InvalidUsername invalidUsername) {
                    JOptionPane.showMessageDialog(null,"Invalid Username", "WARNING", JOptionPane.WARNING_MESSAGE);
                    System.out.println("Invalid Username");
                    UserText.setText("");
                    frame.dispose();
                }
                char[] pass = PassText.getPassword();
                String password = String.valueOf(pass);

                try {
                    int a = coronita.authenticate(user,password);
                    frame.dispose();
                    App app = new App();
                    System.out.println(a);

                } catch (InvalidAccount e) {
                    JOptionPane.showMessageDialog(null,"Invalid Account", "WARNING", JOptionPane.WARNING_MESSAGE);
                    System.out.println("Invalid Account");
                    PassText.setText("");
                }
            }
        });
        panel.add(button);
        button2 = new JButton("Sing up");
        button2.setBounds(100, 80 ,80, 25);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
                SignIn sign = new SignIn();
            }
        });
        panel.add(button2);
        frame.setVisible(true);
    }
    public static void main(String[] args){
        new LogIn();
    }

}
