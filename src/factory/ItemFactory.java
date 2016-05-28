package factory;

import object.*;
import object.GameObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by TuanTai on 28/05/2016.
 */
public class ItemFactory {

    public ArrayList<GameObject> gameObjectArrayList = new ArrayList<>();

    public void loadBonusItem(String filePath) {
        File file = new File(filePath);
        try {
            InputStream inputStream = new FileInputStream(file);
            Scanner scanner = new Scanner(inputStream);

            // get object from files
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                GameObject gameObject = getObject(line);
                if (gameObject != null)
                    gameObjectArrayList.add(gameObject);
//                System.out.println(gameObject);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private GameObject getObject(String line) {
        GameObject gameObject = null;

        String para[] = line.split(",");
        switch (para[0]) {
            case "SNARK_NEST":
                gameObject = new SnarkNest(Integer.parseInt(para[1]));
                break;
            case "AMMO":
                gameObject = new Ammo(Integer.parseInt(para[1]));
                break;
            case "CANNON":
                gameObject = new Cannon(Integer.parseInt(para[1]));
                break;
            case "HINT":
                if (para[1].equalsIgnoreCase("SNARK_HINT"))
                    gameObject = new Hint(Hint.HINT_TYPE.SNARK_HINT);
                else if (para[1].equalsIgnoreCase("ITEM_HINT"))
                    gameObject = new Hint(Hint.HINT_TYPE.ITEM_HINT);
                break;
            default:
                System.out.println("Not identified item");
        }
        return gameObject;
    }

    public GameObject getObjectCopy(int position) {
//        Return a clone of object
        GameObject gameObject = null;
        if (position < gameObjectArrayList.size()) {
            try {
                gameObject = (GameObject) gameObjectArrayList.get(position).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return gameObject;
    }

    public void showObjectsList() {
        System.out.println(gameObjectArrayList);
    }

    public static void main(String[] args) {
        ItemFactory itemFactory = new ItemFactory();
        itemFactory.loadBonusItem("src/ItemList");
//        showObjectsList();

        for (int i = 0; i < itemFactory.gameObjectArrayList.size(); i++) {
            try {
                System.out.println(itemFactory.gameObjectArrayList.get(i).clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

//        System.out.println("Nest befor clone");
//        System.out.println(gameObjectArrayList.get(0));
//
//        GameObject object = getObjectCopy(0);
//        ((SnarkNest) object).setNumSnarkRelease(3);
//
//        System.out.println("Nest after clone");
//        System.out.println(gameObjectArrayList.get(0));
////        System.out.println(getObjectCopy(0));
    }

    public GameObject getRandomBonusObject() {
        Random rand = new Random();
        int pos = rand.nextInt(gameObjectArrayList.size());
        return getObjectCopy(pos);
    }
}
