package COVID.src.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignIn extends JFrame {

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


    public SignIn(){

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setSize(350,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("NICE_COVID_SERVER");
        frame.add(panel);
        panel.setLayout(null);

        NameLabel = new JLabel("Name");
        NameLabel.setBounds(10, 20, 80, 25);
        panel.add(NameLabel);

        NameText = new JTextField ();
        NameText.setBounds(100, 20 ,165,25);
        panel.add(NameText);

        UserLabel = new JLabel("User");
        UserLabel.setBounds(10, 50, 80, 25);
        panel.add(UserLabel);

        UserText = new JTextField ();
        UserText.setBounds(100, 50 ,165,25);
        panel.add(UserText);

        PasswordLabel = new JLabel("Password");
        PasswordLabel.setBounds(10, 80, 80, 25);
        panel.add(PasswordLabel);

        PassText = new JPasswordField();
        PassText.setBounds(100, 80 ,165,25);
        panel.add(PassText);

        PasswordLabel2 = new JLabel("Confirm Password");
        PasswordLabel2.setBounds(10, 100, 80, 25);
        panel.add(PasswordLabel2);

        PassText2 = new JPasswordField();
        PassText2.setBounds(100, 100 ,165,25);
        panel.add(PassText2);

        success = new JLabel("");
        success.setBounds(10,110,300,25);
        panel.add(success);

        button = new JButton("Signin");
        button.setBounds(10, 60 ,80, 25);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user = UserText.getText();
                char[] pass1 = PassText.getPassword();
                char[] pass2 = PassText2.getPassword();

                if(pass1.equals(pass2)){
                    System.out.println("nice");
                }

                else{
                    System.out.println(pass1);
                    System.out.println(pass2);
                    System.out.println("try again");
                }
            }
        });
        panel.add(button);

        frame.setVisible(true);
    }

}
