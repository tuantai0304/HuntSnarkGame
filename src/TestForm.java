import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by TuanTai on 28/05/2016.
 */
public class TestForm extends JFrame {
    private JButton btnClick;
    private JPanel mainPanel;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JComboBox comboBox1;

    public TestForm() throws HeadlessException {
        super("Hello world");

        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        btnClick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Test");
            }
        });
        setVisible(true);
    }
}
