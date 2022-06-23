package study.jpa.basic.entityMapping.code;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Joonhyuck Hyoung
 */
// @Entity(name = "Member")
// 다른 패키지에 같은 클래스를 사용하고 있는 경우에 다른 이름으로 사용하고 싶을 때, 근데 쓰지말자
@Table(name = "MBR") // DB의 실제 테이블명과 매핑
public class MemberEntity {

    public MemberEntity() {
    }
}
