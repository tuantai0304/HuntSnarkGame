import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by TuanTai on 28/05/2016.
 */
public class OptionScreen extends JFrame{
    private JPanel mainPane;
    private JTextField tfName;
    private JComboBox cboSwampSize;
    private JRadioButton rbNumSnark_1;
    private JRadioButton rbNumSnark_5;
    private JRadioButton rbNumSnark_3;
    private JCheckBox cbBonusItems;
    private JButton btnLoad;
    private JButton btnPlay;

    ButtonGroup buttonGroup  = new ButtonGroup();

    GameController gameController;
    MainGameScreen mainGameScreen;
    private Preference preference;

    public OptionScreen() throws HeadlessException {
        super("Option scree");
        setContentPane(mainPane);

        initGUI();

        setListener();
        pack();
        setVisible(true);
    }

    private void setListener() {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnLoad) {
                    loadPlayerPreference();
                } else if (e.getSource() == btnPlay) {
                    playGame();
                } else {
                    System.out.println("Not recognized button");
                }
            }
        };

        btnLoad.addActionListener(listener);
        btnPlay.addActionListener(listener);

    }

    private void playGame() {
        Preference pref = getPreference();
        Player player = new Player(tfName.getText(), pref);

        mainGameScreen = new MainGameScreen(player);

//        gameController = new GameController();

        System.out.println(player);


    }

    private void loadPlayerPreference() {
        JFileChooser chooser = new JFileChooser("/");
        int retrieval = chooser.showOpenDialog(null);
        if (retrieval == JFileChooser.APPROVE_OPTION) {
            try {
                FileInputStream fos = new FileInputStream(chooser.getSelectedFile());
                ObjectInputStream in = new ObjectInputStream(fos);
                Player p = (Player) in.readObject();
                displayPlayerPrefs(p);
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private void displayPlayerPrefs(Player p) {
        Preference prefs = p.getPreference();
        tfName.setText(p.getPlayerName());

        String strSize = prefs.getGridSize() + "x" + prefs.getGridSize();
        int pos = 0;
        for (int i = 0; i < swampSizeArr.length; i++) {
            if (strSize.equalsIgnoreCase(swampSizeArr[i])) {
                pos = i;
            }
        }
        cboSwampSize.setSelectedIndex(pos);


        switch (prefs.getNumSnarkDefault()) {
            case 1: rbNumSnark_1.setSelected(true); break;
            case 3: rbNumSnark_3.setSelected(true); break;
            case 5: rbNumSnark_5.setSelected(true); break;
        }

        if (prefs.isContainBonus()) {
            cbBonusItems.setSelected(true);
        } else {
            cbBonusItems.setSelected(false);
        }
    }

    String[] swampSizeArr = {"5x5","7x7","9x9","13x13"};

    private void initGUI() {
        for (int i = 0; i < swampSizeArr.length; i++) {
            cboSwampSize.addItem(swampSizeArr[i]);
        }

        buttonGroup.add(rbNumSnark_1);
        buttonGroup.add(rbNumSnark_3);
        buttonGroup.add(rbNumSnark_5);
        rbNumSnark_1.setSelected(true);
    }

    public Preference getPreference() {
        String strSize = cboSwampSize.getSelectedItem().toString();
        strSize = strSize.substring(0, strSize.indexOf("x"));
        int size = Integer.parseInt(strSize);
        System.out.println(size);

        int numberSnark;
        if (rbNumSnark_1.isSelected()) {
            numberSnark = 1;
        } else if (rbNumSnark_3.isSelected()) {
            numberSnark = 3;
        } else {
            numberSnark = 5;
        }

        boolean hasBonusItems = cbBonusItems.isSelected();

        Preference preference = new Preference(size, numberSnark, hasBonusItems);
        return preference;
    }

    public static void main(String[] args) {
//        GameController gameController = new GameController();
        OptionScreen os = new OptionScreen();
        os.show();
//        os.playGame();
    }
}
