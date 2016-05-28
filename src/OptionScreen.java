import javax.swing.*;
import java.awt.*;

/**
 * Created by TuanTai on 28/05/2016.
 */
public class OptionScreen extends JFrame{
    private JPanel mainPane;
    private JTextField tfName;
    private JComboBox cboSwampSize;
    private JRadioButton rbNumSnark_1;
    private JRadioButton rbNumSnark_5;
    private JRadioButton rbNumSnark_2;
    private JCheckBox cbBonusItems;
    private JButton btnLoad;
    private JButton btnPlay;

    public OptionScreen() throws HeadlessException {
        super("Option scree");
        setContentPane(mainPane);
        pack();
        setVisible(true);
    }
}
