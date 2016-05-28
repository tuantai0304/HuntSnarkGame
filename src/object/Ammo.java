package object;

/**
 * Created by TuanTai on 28/05/2016.
 */
public class Ammo extends BonusItem {
    private int numExtraShot;

    public int getNumExtraShot() {
        return numExtraShot;
    }

    public void setNumExtraShot(int numExtraShot) {
        this.numExtraShot = numExtraShot;
    }

    public Ammo(int numExtraShot) {
        setNumExtraShot(numExtraShot);
    }

    @Override
    public String toString() {
        return "Ammo{" +
                "numExtraShot=" + numExtraShot +
                '}';
    }
}
