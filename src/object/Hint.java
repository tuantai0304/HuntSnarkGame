package object;

/**
 * Created by TuanTai on 28/05/2016.
 */
public class Hint extends GameObject {
    public enum HINT_TYPE {
        SNARK_HINT, ITEM_HINT
    }

    HINT_TYPE mHintType;

    public HINT_TYPE getmHintType() {
        return mHintType;
    }

    public void setmHintType(HINT_TYPE mHintType) {
        this.mHintType = mHintType;
    }

    @Override
    public String toString() {
        return "Hint{" +
                "mHintType=" + (mHintType == HINT_TYPE.ITEM_HINT? "Item hint":"Snart hint") +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Hint hint = new Hint(mHintType);
        return hint;
    }

    public Hint(HINT_TYPE hint_type) {
        setmGameObjectType(GAMEOBJECT_TYPE.HINT);
        setmHintType(hint_type);
    }
}
