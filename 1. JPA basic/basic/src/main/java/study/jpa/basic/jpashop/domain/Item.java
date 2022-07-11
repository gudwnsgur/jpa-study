package study.jpa.basic.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Joonhyuck Hyoung
 */
@Entity(name = "jpashop_item")
@Table(name = "jpashop_item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "MEMBER_ID") 회사 룰대로 가면 된다.
    private Long no;

    private String name;

    private int price;

    /**
     * 원래는 hibernate가 orderDate -> order_date 로 자동변환해준다.
     * 지금은 안됨?
     * https://www.baeldung.com/hibernate-naming-strategy
     */
    @Column(name = "stock_quantity")
    private int stockQuantity;

    public Long getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
}
