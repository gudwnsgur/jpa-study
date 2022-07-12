package study.jpa.basic.relationMappingBasic.code;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Joonhyuck Hyoung
 */
@Entity(name = "relation_mapping_basic_team")
@Table(name = "relation_mapping_basic_team")
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamNo;

    private String teamName;

    public Team() {
    }

    public Team(Long teamNo, String teamName) {
        this.teamNo = teamNo;
        this.teamName = teamName;
    }

    public Long getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(Long teamNo) {
        this.teamNo = teamNo;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public static Team create(String teamName) {
        Team team = new Team();
        team.setTeamName(teamName);
        return team;
    }
}
