package object;

import java.awt.*;

/**
 * Created by TuanTai on 28/05/2016.
 */
public abstract class GameObject implements Cloneable{
    public enum GAMEOBJECT_TYPE {
        SNARK, BONUSITEM, CANNON, HINT, SNARKNEST
    }

    GAMEOBJECT_TYPE mGameObjectType;
    Point position;

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public GAMEOBJECT_TYPE getmGameObjectType() {
        return mGameObjectType;
    }

    public void setmGameObjectType(GAMEOBJECT_TYPE mGameObjectType) {
        this.mGameObjectType = mGameObjectType;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
