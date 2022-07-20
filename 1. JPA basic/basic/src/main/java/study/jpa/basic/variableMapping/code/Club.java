package study.jpa.basic.variableMapping.code;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Joonhyuck Hyoung
 */
@Entity
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubNo;

    private String clubName;

    @OneToMany
    @JoinColumn(name = "clubNo")
    private List<Player> players = new ArrayList<>();

    public static Club create(String clubName){
        Club club = new Club();
        club.setClubName(clubName);
        return club;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public long getClubNo() {
        return clubNo;
    }

    public void setClubNo(long clubNo) {
        this.clubNo = clubNo;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public Club() {
    }

    public Club(long clubNo, String clubName) {
        this.clubNo = clubNo;
        this.clubName = clubName;
    }
}
