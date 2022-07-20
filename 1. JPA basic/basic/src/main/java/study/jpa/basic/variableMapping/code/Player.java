package study.jpa.basic.variableMapping.code;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Joonhyuck Hyoung
 */
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerNo;

    private String playerName;


    public static Player create(String playerName) {
        Player player = new Player();
        player.setPlayerName(playerName);
        return player;
    }

    public long getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(long playerNo) {
        this.playerNo = playerNo;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Player() {
    }

    public Player(long playerNo, String playerName) {
        this.playerNo = playerNo;
        this.playerName = playerName;
    }
}
