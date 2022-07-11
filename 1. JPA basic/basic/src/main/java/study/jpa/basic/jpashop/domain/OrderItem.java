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
@Entity(name = "jpashop_order_item")
@Table(name = "jpashop_order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "item_no")
    private Long itemNo;

    @Column(name = "order_price")
    private int orderPrice;
    private int count;

    public Long getNo() {
        return no;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public Long getItemNo() {
        return itemNo;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public int getCount() {
        return count;
    }
}
