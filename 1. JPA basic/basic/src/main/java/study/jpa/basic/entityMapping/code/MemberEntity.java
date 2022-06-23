package study.jpa.basic.entityMapping.code;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Joonhyuck Hyoung
 */
@Entity
public class MemberEntity {
    @Id
    private Long id;

    private String name;

    public MemberEntity() {
    }
}
