package object;

/**
 * Created by TuanTai on 28/05/2016.
 */
public class Cannon extends GameObject {
    private int blastRadius;

    public Cannon(int blastRadius) {
        setmGameObjectType(GAMEOBJECT_TYPE.CANNON);
        setBlastRadius(blastRadius);
    }

    public int getBlastRadius() {
        return blastRadius;
    }

    public void setBlastRadius(int blastRadius) {
        this.blastRadius = blastRadius;
    }

    @Override
    public String toString() {
        return "Cannon{" +
                "blastRadius=" + blastRadius +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Cannon cannon = new Cannon(blastRadius);
        return cannon;
    }
}
