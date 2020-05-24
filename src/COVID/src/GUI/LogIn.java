package COVID.src.GUI;

import COVID.src.Coronita.CoronitaClientAccount;
import COVID.src.Exceptions.AccountExceptions.InvalidAccount;
import COVID.src.Exceptions.AccountExceptions.MismatchPassException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LogIn extends JFrame {

        private static JLabel UserLabel;
        private static JTextField UserText;
        private static JTextField EstimateGlobal;
        private static JTextField EstimateCountry;
        private static JLabel PasswordLabel;
        private static JPasswordField PassText;
        private static JLabel CountryLable;
        private JList<String> countryList;
        private JScrollPane country;
        private static JButton button;
        private static JButton button2;
        private CoronitaClientAccount coronita;
        private String s;
        private Object selectedItem;

    public LogIn(){
        try {
            this.EstimateGlobal = new JTextField("Global");
            this.EstimateCountry = new JTextField("Country");
            this.coronita = new CoronitaClientAccount(EstimateGlobal,EstimateCountry);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        DefaultListModel<String> listCountries = new DefaultListModel<>();
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
        PasswordLabel.setBounds(10, 60, 80, 25);
        panel.add(PasswordLabel);

        PassText = new JPasswordField();
        PassText.setBounds(100, 60 ,165,25);
        panel.add(PassText);

        CountryLable = new JLabel("Password");
        CountryLable.setBounds(10, 100, 80, 25);
        panel.add(CountryLable);

        listCountries.addElement("Portugal");listCountries.addElement("Spain");listCountries.addElement("Italy");listCountries.addElement("China");
        countryList = new JList<>(listCountries);
        country = new JScrollPane(countryList);
        country.setBounds(100,100,100,25);
        countryList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if(countryList.getSelectedIndex()!=-1){
                    if(countryList.getSelectedValue().equals("Portugal")){
                         s = "PT";
                    }
                    if(countryList.getSelectedValue().equals("Spain")){
                         s = "ES";
                    }
                    if(countryList.getSelectedValue().equals("Italy")){
                         s = "IT";
                    }
                    if(countryList.getSelectedValue().equals("China")){
                         s = "CN";
                    }

                }
                else s="PT";
            }
        });
        panel.add(country);

        button = new JButton("Login");
        button.setBounds(30, 80 ,80, 25);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user = UserText.getText();
                char[] pass = PassText.getPassword();
                String password = String.valueOf(pass);
                try {
                    int a = coronita.authenticate(user, password);
                    coronita.setCountry(s);
                    frame.dispose();
                    App app = new App(user,a, EstimateGlobal, EstimateCountry, coronita);
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
                    frame.dispose();
                }
            }
        });
        panel.add(button);
        button2 = new JButton("Sing up");
        button2.setBounds(120, 80 ,80, 25);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
                SignIn sign = new SignIn(EstimateGlobal,EstimateCountry);
            }
        });
        panel.add(button2);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        new LogIn();
    }

}
