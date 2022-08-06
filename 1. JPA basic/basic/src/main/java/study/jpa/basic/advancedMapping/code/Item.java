package study.jpa.basic.advancedMapping.code;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * @author Joonhyuck Hyoung
 */
@Entity
// 이걸 추가하면 조인전략으로 만들 수 있다.
@Inheritance(strategy = InheritanceType.JOINED)
// DTYPE :  구분할 수 있는 컬럼이 만들어진다.
@DiscriminatorColumn
public class Item {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

