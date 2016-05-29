

import object.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by TuanTai on 28/05/2016.
 */
public class MainGameScreen extends JFrame {
    private JTextArea someCueTextArea;
    private JButton optionsButton;
    private JButton saveButton;
    private JButton newGameButton;
    private JPanel mainPane;
    private JPanel gameGridPane;
    private JPanel eastPane;
    private JPanel bottomPane;
    private JPanel infoPane;
    private JPanel cluePane;
    private JLabel tfName;
    private JLabel tfNumCatchedSnark;
    private JLabel tfRemainAmmo;
    private JLabel tfResult;

    int numCatchedSnark;
    int numRemainAmmo;

    private final String ASSETS = "assets/";
    private final String TILE_BONUS_AMMO = ASSETS + "tile_bonus_ammo.png";
    private final String TILE_BONUS_CANNON = ASSETS + "tile_bonus_cannon.png";
    private final String TILE_BONUS_HINT = ASSETS + "tile_bonus_hint.png";
    private final String TILE_SNARK = ASSETS + "tile_snark.png";
    private final String TILE_EMPTY = ASSETS + "tile_empty.png";
    private final String TILE_UNSEARCHED = ASSETS + "tile_unsearched.png";

    private final String RES_LOSE = "YOU LOSE, MAN";
    private final String RES_WIN = "CONGRATULATION, WINNER";

    private final int DEFAULT_AMMO = 10;
    private final int DEFAULT_CATCHED_SNARK = 0;



    JButton[][] jButtonArr;
    GameController gameController;

    public MainGameScreen(Player player) {
        super("Main game Screen");
        setContentPane(mainPane);

        this.player = player;
        this.preference = player.getPreference();

        initGame();
        pack();
        setVisible(true);
    }


    Preference preference;
    Player player;

    private void initGame() {

//        Todo: // FIXME: 28/05/2016
//        preference = new Preference(10, 5, true);
//        player = new Player("Tai", preference);

        gameController = new GameController(player);
        newGame();
        initFunctionButtons();
    }

    private void initFunctionButtons() {
        ActionListener funtionListenner = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == optionsButton) {
                    showOptionScreen();
                } else if (e.getSource() == saveButton) {
                    saveFile();
                } else if (e.getSource() == newGameButton) {
                    newGame();
                } else {
                    System.out.println("Not recognized button");
                }

            }
        };

        optionsButton.addActionListener(funtionListenner);
        saveButton.addActionListener(funtionListenner);
        newGameButton.addActionListener(funtionListenner);

    }

    public void newGame() {
        resetParam();
        initGameGUI();
    }

    private void resetParam() {
        numRemainAmmo = DEFAULT_AMMO;
        numCatchedSnark = DEFAULT_CATCHED_SNARK;
    }

    private void saveFile() {
        JFileChooser chooser = new JFileChooser("/");

        int retrieval = chooser.showSaveDialog(null);
        if (retrieval == JFileChooser.APPROVE_OPTION) {
            try {
                FileOutputStream fos = new FileOutputStream(chooser.getSelectedFile());
                ObjectOutputStream out = new ObjectOutputStream(fos);
                out.writeObject(player);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showOptionScreen() {
        new OptionScreen().setVisible(true);
        this.setVisible(false);
    }

    private void initGameGUI() {
        gameGridPane.removeAll();

        int size = preference.getGridSize();
        jButtonArr = new JButton[size][size];
        gameGridPane.setLayout(new GridLayout(size, size));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton btn = new JButton();
                btn.addActionListener(gameGridActionListener);
                btn.setIcon(getResizeImage(TILE_UNSEARCHED));
//                btn.setPreferredSize(new Dimension(10,10));
//                btn.setMaximumSize(new Dimension(10,10));
                btn.setSize(10,10);
                btn.setMargin(new Insets(0,0,0,0));
                btn.putClientProperty("row", i);
                btn.putClientProperty("column", j);

                jButtonArr[i][j] = btn;
                gameGridPane.add(btn);
            }
        }

        tfName.setText(player.getPlayerName());
        tfResult.setVisible(true);

        pack();

        updateCatchedSnark();
        updateRemainAmmo();
    }

    private ImageIcon getResizeImage(String filePath) {
        ImageIcon icon = new ImageIcon(filePath);
        Image img = icon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        icon = new ImageIcon( img );
        return icon;
    }

    private void updateRemainAmmo() {
        tfRemainAmmo.setText(String.valueOf(numRemainAmmo));
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

    private boolean checkGameEnd() {
        boolean isEnd = false;
        if (numCatchedSnark == preference.getNumSnarkDefault()) {
            isEnd = true;
            updateCatchedSnark();
            setVisible(true);
            tfResult.setText(RES_WIN);
            setPanelEnabled(gameGridPane, false);
        }
        if (numRemainAmmo <= 0) {
            isEnd = true;
            updateRemainAmmo();
            tfResult.setVisible(true);
            tfResult.setText(RES_LOSE);
            setPanelEnabled(gameGridPane, false);
        }
        return isEnd;
    }

    void setPanelEnabled(JPanel panel, Boolean isEnabled) {
        panel.setEnabled(isEnabled);

        Component[] components = panel.getComponents();

        for (int i = 0; i < components.length; i++) {
            if (components[i].getClass().getName() == "javax.swing.JPanel") {
                setPanelEnabled((JPanel) components[i], isEnabled);
            }

            components[i].setEnabled(isEnabled);
        }
    }

    //    Todo: make sure only add ammo, no deduct, and function checkEndGame not working
    private void blastSurrounding(int row, int col, int blastRadius) {
//        If user click on cannon, blast surrouding start from (row, col) with blastRadius
//        Then get all the effect of nearby cell
        int lowerRow = row - blastRadius;
        int upperRow = row + blastRadius;
        int lowerCol = col - blastRadius;
        int upperCol = col + blastRadius;

        int gridSize = preference.getGridSize();

        if (lowerRow < 0) lowerRow = 0;
        if (upperRow >= gridSize) upperRow = gridSize - 1;
        if (lowerCol < 0) lowerCol = 0;
        if (upperCol >= gridSize) upperCol = gridSize - 1;

        for (int i = lowerRow; i <= upperRow; i++) {
            for (int j = lowerCol; j <= upperCol; j++) {
                if (jButtonArr[i][j].isEnabled() && (i != row || j != col)) {
                    jButtonArr[i][j].setEnabled(false);
                    if (gameController.getItemsFrom(i, j) != null) {
                        getEffect(jButtonArr[i][j]);
                    } else {
                        jButtonArr[i][j].setDisabledIcon(new ImageIcon(TILE_EMPTY));
                    }
                }
            }
        }
    }

    private void getEffect(JButton jButton) {
        int row = (int) jButton.getClientProperty("row");
        int col = (int) jButton.getClientProperty("column");

        boolean isSnark = false;

        GameObject gameObject = gameController.getItemsFrom(row, col);

        if (gameObject != null) {
            switch (gameObject.getmGameObjectType()) {
                case SNARK:
                    numCatchedSnark++;
                    updateCatchedSnark();
                    jButtonArr[row][col].setDisabledIcon(getResizeImage(TILE_SNARK));
                    isSnark = true;
                    break;

                case CANNON:
                    jButtonArr[row][col].setDisabledIcon(getResizeImage(TILE_BONUS_CANNON));
                    blastSurrounding(row, col, ((Cannon) gameObject).getBlastRadius());

                    break;

                case BONUSITEM:
                    numRemainAmmo += ((Ammo) gameObject).getNumExtraShot();
                    jButtonArr[row][col].setDisabledIcon(getResizeImage(TILE_BONUS_AMMO));
                    updateRemainAmmo();
                    break;

                case HINT:
//                    Todo: getHint hear
                    GameObject hint = getAHintObject(gameObject);
//                    getEffect(jButtonArr[randObject.getPosition().x][randObject.getPosition().y]);
                    if (hint != null) {
                        jButtonArr[hint.getPosition().x][hint.getPosition().y].setBackground(Color.red);
                        jButtonArr[row][col].setDisabledIcon(getResizeImage(TILE_BONUS_HINT));
                    }
                    break;

                case SNARKNEST:
//                    Todo: open a random Snark, add NumberCatchedSnark
                    GameObject snark = getAHintObject(gameObject);
                    if (snark != null) {
//                    getEffect(jButtonArr[randObject.getPosition().x][randObject.getPosition().y]);
                        jButtonArr[snark.getPosition().x][snark.getPosition().y].setBackground(Color.red);
                    }
                    break;
                default:
                    break;
            }

            System.out.println(gameObject);
        } else {
            numRemainAmmo--;
            updateRemainAmmo();
            jButtonArr[row][col].setDisabledIcon(getResizeImage(TILE_EMPTY));
        }

        if (!isSnark) {
            showCluesOfNeareastSnarkFrom(row, col);
        }

        checkGameEnd();
    }

    public GameObject getAHintObject(GameObject typeOfHint) {
//        Get a Snark or a Bonus item depend on typeOfHint
//        TypeOfHint: a Hint object or a SnarkNest Object

        GameObject randObject = null;
        if (typeOfHint.getmGameObjectType() == GameObject.GAMEOBJECT_TYPE.HINT) {
            randObject = gameController.getHint((Hint) typeOfHint);
        } else if (typeOfHint.getmGameObjectType() == GameObject.GAMEOBJECT_TYPE.SNARKNEST) {
            randObject = gameController.getARandomSnark();
        }
        int rand_x = randObject.getPosition().x;
        int rand_y = randObject.getPosition().y;

        while (!jButtonArr[rand_x][rand_y].isEnabled()) {
            if (typeOfHint.getmGameObjectType() == GameObject.GAMEOBJECT_TYPE.HINT) {
                randObject = gameController.getHint((Hint) typeOfHint);
            } else if (typeOfHint.getmGameObjectType() == GameObject.GAMEOBJECT_TYPE.SNARKNEST) {
                randObject = gameController.getARandomSnark();
            }
            rand_x = randObject.getPosition().x;
            rand_y = randObject.getPosition().y;
        }
        return randObject;
    }

    private void showCluesOfNeareastSnarkFrom(int row, int col) {
        Snark nearestSnark = gameController.getNearestSnark(row, col);
        int nearSnark_X = nearestSnark.getPosition().x;
        int nearSnark_Y = nearestSnark.getPosition().y;
        String strNearestSnark = "North - South - East - West";

        if (nearSnark_X < row) strNearestSnark = strNearestSnark.replace("South - ", "");
        if (nearSnark_X > row) strNearestSnark = strNearestSnark.replace("North - ", "");
        if (nearSnark_X == row) strNearestSnark = strNearestSnark.replace("North - South - ", "");

        if (nearSnark_Y > col) strNearestSnark = strNearestSnark.replace(" - West", "");
        if (nearSnark_Y < col) strNearestSnark = strNearestSnark.replace("East - ", "");
        if (nearSnark_Y == col) strNearestSnark = strNearestSnark.replace(" - East - West", "");

        setClues(strNearestSnark);

    }

    public void setClues(String strNewStr) {
        String clue = someCueTextArea.getText() + "\n "
                + strNewStr;

        someCueTextArea.setText(clue);
    }

    private void updateCatchedSnark() {
        tfNumCatchedSnark.setText(String.format("%d/%d", numCatchedSnark, preference.getNumSnarkDefault()));
    }


    public static void main(String[] args) {
        MainGameScreen mainGameScreen = new MainGameScreen(null);
        for (int i = 0; i < mainGameScreen.preference.getGridSize(); i++) {
            for (int j = 0; j < mainGameScreen.preference.getGridSize(); j++) {
                GameObject[][] gameGrid = mainGameScreen.gameController.getGameGrid();
                if (gameGrid[i][j] != null)
                    System.out.print(" " + gameGrid[i][j] + " ");
                else
                    System.out.print(" null ");
            }
            System.out.println("\n");
//        }
        }

    }

    public void setPlayer(Player player) {
        this.player = player;
        this.preference = player.getPreference();
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
