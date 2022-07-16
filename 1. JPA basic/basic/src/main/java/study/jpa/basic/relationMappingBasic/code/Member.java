package study.jpa.basic.relationMappingBasic.code;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Joonhyuck Hyoung
 */
@Entity(name = "relation_mapping_basic_member")
@Table(name = "relation_mapping_basic_member")
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    private String userName;


    // JPA 에게 둘의 관계를 알려줘야 한다.
    // member 입장에서 Many, team 입장에서 One
    @ManyToOne
    // join 해야하는 컬럼 명시
    @JoinColumn(name = "teamNo")
    private Team team;

    public Member() {
    }

    public Member(Long no, String userName, Team team) {
        this.no = no;
        this.userName = userName;
        this.team = team;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Member{" + "no=" + no + ", userName='" + userName + '\'' + ", team=" + team.toString() + '}';
    }

    public static Member create(Team team, String userName) {
        Member member = new Member();
        member.setTeam(team);
        member.setUserName(userName);
        return member;
    }
}
