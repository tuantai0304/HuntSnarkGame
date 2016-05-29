import java.io.Serializable;

/**
 * Created by TuanTai on 28/05/2016.
 */
public class Player implements Serializable {
    private String playerName;
    private Preference preference;

    public Player(String playerName, Preference preference) {
        setPlayerName(playerName);
        setPreference(preference);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerName='" + playerName + '\'' +
                ", preference=" + preference +
                '}';
    }
}
