package study.jpa.basic;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Joonhyuck Hyoung
 * @Entity : JPA가 로딩될때 관리해야 하는 객체로 인식
 * @Id : 최소한 pk가 무엇인지는 알려주어야 한다.
 * @Table(name = "") : 다른 이름의 테이블과 매핑
 * @Column(name = "") : 다른 이름의 컬럼과 매핑
 */

@Entity
public class Member {

    @Id
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
