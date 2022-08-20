package study.jpa.basic.proxy.code;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Joonhyuck Hyoung
 */
@Entity(name = "proxy_team")
@Table(name = "proxy_team")
public class Team {
    @Id @GeneratedValue
    private Long id;

    private String teamName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
