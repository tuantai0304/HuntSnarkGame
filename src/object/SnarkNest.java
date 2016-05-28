package object;

/**
 * Created by TuanTai on 28/05/2016.
 */
public class SnarkNest extends GameObject {
    private int numSnarkRelease;

    public int getNumSnarkRelease() {
        return numSnarkRelease;
    }

    public void setNumSnarkRelease(int numSnarkRelease) {
        this.numSnarkRelease = numSnarkRelease;
    }

    public SnarkNest(int numSnarkRelease) {
        setmGameObjectType(GAMEOBJECT_TYPE.SNARKNEST);
        setNumSnarkRelease(numSnarkRelease);
    }

    @Override
    public String toString() {
        return "SnarkNest{" +
                "numSnarkRelease=" + numSnarkRelease +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SnarkNest snarkNest = new SnarkNest(numSnarkRelease);
        return snarkNest;
    }
}
