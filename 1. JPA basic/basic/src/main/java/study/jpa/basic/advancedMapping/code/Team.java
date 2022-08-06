package study.jpa.basic.advancedMapping.code;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Joonhyuck Hyoung
 */
@Entity
public class Team extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private String name;
}
