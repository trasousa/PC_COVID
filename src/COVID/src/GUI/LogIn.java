package COVID.src.GUI;

import COVID.src.Coronita.CoronitaClientAccount;
import COVID.src.Coronita.CoronitaServer;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn extends JFrame {

        private static JLabel UserLabel;
        private static JTextField UserText;
        private static JLabel PasswordLabel;
        private static JPasswordField PassText;
        private static JLabel success;
        private static JButton button;
        private CoronitaClientAccount coronita;

    public LogIn(){

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
                    invalidUsername.printStackTrace();
                }
                char[] pass = PassText.getPassword();
                if(coronita.authenticate()){
                    frame.dispose();
                    System.out.println("nice");
                    App app = (new App());

                }

                else{
                    frame.dispose();
                    SignIn sign = (new SignIn());
                }
            }
        });
        panel.add(button);

        frame.setVisible(true);
    }
    public static void main(String[] args){
        new LogIn();
    }

}
