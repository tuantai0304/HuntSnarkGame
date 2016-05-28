import factory.ItemFactory;
import object.BonusItem;
import object.GameObject;
import object.Hint;
import object.Snark;

import java.awt.*;
import java.util.Random;

/**
 * Created by TuanTai on 28/05/2016.
 */
public class GameController {
    GameObject[][] gameGrid;
    Player mPlayer;
    ItemFactory mItemFactory;

    public GameController(Player mPlayer) {
        setmPlayer(mPlayer);

        mItemFactory = new ItemFactory();
        mItemFactory.loadBonusItem("src/ItemList");

        generateGameGrid();
    }

    public GameObject[][] getGameGrid() {
        return gameGrid;
    }

    public void setGameGrid(GameObject[][] gameGrid) {
        this.gameGrid = gameGrid;
    }

    public Player getmPlayer() {
        return mPlayer;
    }

    public void setmPlayer(Player mPlayer) {
        this.mPlayer = mPlayer;
    }

    public ItemFactory getmItemFactory() {
        return mItemFactory;
    }

    public void setmItemFactory(ItemFactory mItemFactory) {
        this.mItemFactory = mItemFactory;
    }



    public void generateGameGrid() {
        Preference preference = mPlayer.getPreference();
        int size = preference.getGridSize();

        gameGrid = new GameObject[size][size];

//        Generate random snarks
        Random rand = new Random();

        for (int i = 0; i < preference.getNumSnarkDefault(); i++) {
            int row = rand.nextInt(size);
            int col = rand.nextInt(size);

//            Make sure there is no duplicate Snark position
            while (gameGrid[row][col] != null) {
                row = rand.nextInt(size);
                col = rand.nextInt(size);
            }

            gameGrid[row][col] = new Snark();
//            gameGrid[row][col].setPosition(new Point(row, col));
        }

//        Generate Items List or just left empty if Preference has containBonus = true
        if (preference.isContainBonus()) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (gameGrid[i][j] == null) {
                        // If rand = 0, get a random object from ItemFactory, else just left empty
                        if (rand.nextInt(2) == 0)
                            gameGrid[i][j] = mItemFactory.getRandomBonusObject();

                    }
                }
            }
        }
    }

    GameObject getItemsFrom(int row, int col) {
        if (checkItemAt(row, col)) {
            return gameGrid[row][col];
        }
        return null;
    }

    boolean checkItemAt(int row, int col) {
        if (gameGrid[row][col] != null) {
            return true;
        }
        return false;
    }

    String getHint(Hint hint) {
        switch (hint.getmHintType()) {
            case ITEM_HINT:
                break;
            case SNARK_HINT:
                break;
        }
        return "I am a hint";
    }

    public Snark getNearestSnark(int row, int col) {
        boolean isFound = false;
        Snark snark = null;
        int radius = 1;

        while (!isFound) {
            int lowerRow = row - radius;
            int upperRow = row + radius;
            int lowerCol = col - radius;
            int upperCol = col + radius;

//            Because gameGrid is a square
            if (lowerRow < 0) lowerRow = 0;
            if (upperRow >= gameGrid.length) upperRow = gameGrid.length-1;
            if (lowerCol < 0) lowerCol = 0;
            if (upperCol >= gameGrid.length) upperCol = gameGrid.length-1;

//            Try to find a snark within a radius, if not found, increas radius
            for (int i = lowerRow; i <= upperRow; i++) {
                for (int j = lowerCol; j <= upperCol; j++) {
                    if (gameGrid[i][j] != null && (gameGrid[i][j]) instanceof Snark) {
                        isFound = true;
                        snark = (Snark) gameGrid[i][j];
                    }
                }
            }

            radius++;
        }

        return snark;
    }

    public BonusItem getNearestBonusItem(int row, int col) {
        boolean isFound = false;
        BonusItem bonusItem = null;
        int radius = 1;

        while (!isFound) {
            int lowerRow = row - radius;
            int upperRow = row + radius;
            int lowerCol = col - radius;
            int upperCol = col + radius;

//            Because gameGrid is a square
            if (lowerRow < 0) lowerRow = 0;
            if (upperRow >= gameGrid.length) upperRow = gameGrid.length-1;
            if (lowerCol < 0) lowerCol = 0;
            if (upperCol >= gameGrid.length) upperCol = gameGrid.length-1;

//            Try to find a snark within a radius, if not found, increas radius
            for (int i = lowerRow; i <= upperRow; i++) {
                for (int j = lowerCol; j <= upperCol; j++) {
                    if (gameGrid[i][j] != null && (gameGrid[i][j]) instanceof BonusItem) {
                        isFound = true;
                        bonusItem = (BonusItem) gameGrid[i][j];
                    }
                }
            }

            radius++;
        }

        return bonusItem;
    }



    public void saveToFile(String filePath) {
//        Todo: save here
    }

    public static void main(String[] args) {
//        Todo: fix hardcode
        Preference preference = new Preference(7, 10, false);
        Player player = new Player("Tai", preference);

        GameController gameController = new GameController(player);

        for (int i = 0; i < preference.getGridSize(); i++) {
            for (int j = 0; j < preference.getGridSize(); j++) {
                GameObject[][] gameGrid = gameController.getGameGrid();
                if (gameGrid[i][j] != null)
                    System.out.print(" "+gameGrid[i][j]+ " ");
                else
                    System.out.print(" null ");
            }
            System.out.println("\n");
        }
    }

}
