package study.jpa.basic.proxy.code;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Joonhyuck Hyoung
 */
@Entity(name = "proxy_member")
@Table(name = "proxy_member")
public class Member {
    @Id @GeneratedValue
    private Long id;

    private String userName;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team team;

    static Member create(String userName) {
        Member member = new Member();
        member.setUserName(userName);
        return member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
