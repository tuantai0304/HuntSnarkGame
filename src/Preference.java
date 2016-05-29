import java.io.Serializable;

/**
 * Created by TuanTai on 28/05/2016.
 */
public class Preference implements Serializable{
    private int gridSize;
    private int numSnarkDefault;
    private boolean containBonus;

    public Preference(int gridSize, int numSnarkDefault, boolean containBonus) {
        setContainBonus(containBonus);
        setGridSize(gridSize);
        setNumSnarkDefault(numSnarkDefault);
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public int getNumSnarkDefault() {
        return numSnarkDefault;
    }

    public void setNumSnarkDefault(int numSnarkDefault) {
        this.numSnarkDefault = numSnarkDefault;
    }

    public boolean isContainBonus() {
        return containBonus;
    }

    @Override
    public String toString() {
        return "Preference{" +
                "gridSize=" + gridSize +
                ", numSnarkDefault=" + numSnarkDefault +
                ", containBonus=" + containBonus +
                '}';
    }

    public void setContainBonus(boolean containBonus) {
        this.containBonus = containBonus;
    }
}
