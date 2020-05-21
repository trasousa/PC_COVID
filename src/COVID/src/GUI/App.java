package COVID.src.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {

    private static JLabel Casos;
    private static JTextField CasosText;
    private static JLabel success;
    private static JButton button1;
    private static JButton button2;


    public App() {

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("NICE_COVID_SERVER");
        frame.add(panel);
        panel.setLayout(null);

        Casos = new JLabel("Nº de casos conhecidos");
        Casos.setBounds(10, 20, 80, 25);
        panel.add(Casos);

        CasosText = new JTextField();
        CasosText.setBounds(100, 20, 165, 25);
        panel.add(CasosText);

        success = new JLabel("");
        success.setBounds(10, 110, 300, 25);
        panel.add(success);

        button1 = new JButton("Atualizar");
        button1.setBounds(10, 60, 80, 25);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            }
        });
        panel.add(button1);

        button2 = new JButton("Consultar total de casos reportados");
        button2.setBounds(10, 100, 80, 25);
        button2.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent actionEvent) {

                panel.add(button2);

                frame.setVisible(true);
            }
        });

    }
}
