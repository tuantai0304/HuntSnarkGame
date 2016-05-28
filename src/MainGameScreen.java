

import com.sun.javafx.binding.StringFormatter;
import object.Ammo;
import object.Cannon;
import object.GameObject;
import object.Hint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by TuanTai on 28/05/2016.
 */
public class MainGameScreen extends JFrame{
    private JTextArea someCueTextArea;
    private JButton optionsButton;
    private JButton saveButton;
    private JButton newGameButton;
    private JPanel mainPane;
    private JPanel westPane;
    private JPanel eastPane;
    private JPanel bottomPane;
    private JPanel infoPane;
    private JPanel cluePane;
    private JLabel tfName;
    private JLabel tfNumCatchedSnark;
    private JLabel tfRemainAmmo;
    private JLabel tfResult;

    int size;
    int numCatchedSnark;
    int numRemainAmmo;

    JButton[][] jButtonArr;
    GameController gameController;

    public MainGameScreen(int size) {
        super("Main game Screen");

        this.size = size;
        setContentPane(mainPane);
        initGame();
        pack();
        setVisible(true);
    }


    Preference preference;
    Player player;

    private void initGame() {

//        Todo: // FIXME: 28/05/2016
        preference = new Preference(7, 10, true);
        player = new Player("Tai", preference);

        gameController = new GameController(player);
        initGameParameters();
        initGameGUI();
    }

    private void initGameGUI() {
        int size = preference.getGridSize();
        jButtonArr = new JButton[size][size];
        westPane.setLayout(new GridLayout(size, size));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton btn = new JButton("btn" + (i*size+(j+1)));
                btn.addActionListener(gameGridActionListener);
                btn.putClientProperty("row", i);
                btn.putClientProperty("column", j);

                jButtonArr[i][j] = btn;
                westPane.add(btn);
            }
        }

        tfName.setText(player.getPlayerName());
        tfResult.setVisible(false);

        updateCatchedSnark();
        updateRemainAmmo();
    }

    private void updateRemainAmmo() {
        tfRemainAmmo.setText(String.valueOf(numRemainAmmo));
        System.out.println("num ammo" + numRemainAmmo);
        if (numRemainAmmo <= 0) {
            tfResult.setText("You Lose");
            tfResult.setVisible(true);
        }
    }

    private void initGameParameters() {
        numCatchedSnark = 0;
        numRemainAmmo = 5;
    }

    ActionListener gameGridActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            int row = (int) button.getClientProperty("row");
            int col = (int) button.getClientProperty("column");

            jButtonArr[row][col].setEnabled(false);
            getEffect(button);
        }
    };

    private void blastSurrounding(int row, int col, int blastRadius) {
//        If user click on cannon, blast surrouding start from (row, col) with blastRadius
//        Then get all the effect of nearby cell
        int lowerRow = row - blastRadius;
        int upperRow = row + blastRadius;
        int lowerCol = col - blastRadius;
        int upperCol = col + blastRadius;

        int gridSize = preference.getGridSize();

        if (lowerRow < 0) lowerRow = 0;
        if (upperRow >= gridSize) upperRow = gridSize-1;
        if (lowerCol < 0) lowerCol = 0;
        if (upperCol >= gridSize) upperCol = gridSize-1;

        for (int i = lowerRow; i <= upperRow; i++) {
            for (int j = lowerCol; j <= upperCol; j++) {
                if (jButtonArr[i][j].isEnabled()) {
                    jButtonArr[i][j].setEnabled(false);
                    getEffect(jButtonArr[i][j]);
                }
            }
        }

    }

    private void getEffect(JButton jButton) {
        int row = (int) jButton.getClientProperty("row");
        int col = (int) jButton.getClientProperty("column");

        GameObject gameObject = gameController.getItemsFrom(row, col);

        if (gameObject != null) {
            switch (gameObject.getmGameObjectType()) {
                case SNARK:
                    numCatchedSnark++;
                    updateCatchedSnark();
                    break;

                case CANNON:
                    blastSurrounding(row, col, ((Cannon)gameObject).getBlastRadius());
                    break;

                case BONUSITEM:
                    numRemainAmmo += ((Ammo)gameObject).getNumExtraShot();
                    updateRemainAmmo();
                    break;

                case HINT:
//                    Todo: getHint hear
                    someCueTextArea.setText(gameController.getHint((Hint) gameObject));
                    break;

                case SNARKNEST:
//                    Todo: open a random Snark, add NumberCatchedSnark
                    break;
                default:
                    break;
            }

            System.out.println(gameObject);
        }
        else {
            numRemainAmmo--;
            updateRemainAmmo();
        }

    }

    private void updateCatchedSnark() {
        tfNumCatchedSnark.setText(String.format("%d/%d",numCatchedSnark, preference.getNumSnarkDefault()));
        if (numCatchedSnark == preference.getNumSnarkDefault()) {
            tfResult.setText("You WIN");
        }
    }


    public static void main(String[] args) {
        MainGameScreen mainGameScreen = new MainGameScreen(7);

        Preference preference = mainGameScreen.preference;
        for (int i = 0; i < preference.getGridSize(); i++) {
            for (int j = 0; j < preference.getGridSize(); j++) {
                GameObject[][] gameGrid = mainGameScreen.gameController.getGameGrid();
                if (gameGrid[i][j] != null)
                    System.out.print(" "+gameGrid[i][j]+ " ");
                else
                    System.out.print(" null ");
            }
            System.out.println("\n");
        }
    }


}
