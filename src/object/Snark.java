package object;

/**
 * Created by TuanTai on 28/05/2016.
 */
public class Snark extends GameObject {

    public Snark() {
        setmGameObjectType(GAMEOBJECT_TYPE.SNARK);
    }

    @Override
    public String toString() {
        return String.format("Snark{%d, %d}", position.x, position.y);
    }
}
