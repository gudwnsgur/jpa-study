package study.jpa.basic.relationMappingBasic.code;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    // Team이 One, Member가 Many
    // mappedBy : 나는 이걸로(team) 매핑되어 있는 애야~
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

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


    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Team{" + "teamNo=" + teamNo + ", teamName='" + teamName + '\'' + '}';
    }

    public static Team create(String teamName) {
        Team team = new Team();
        team.setTeamName(teamName);
        return team;
    }
}
