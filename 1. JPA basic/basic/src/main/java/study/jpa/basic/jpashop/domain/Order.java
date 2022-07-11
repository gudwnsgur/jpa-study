package study.jpa.basic.jpashop.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Joonhyuck Hyoung
 */
@Entity(name = "jpashop_order")
@Table(name = "jpashop_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name = "member_no")
    private Long memberNo;

    @Column(name = "ordered_at")
    private LocalDateTime orderedAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Long getNo() {
        return no;
    }

    public Long getMemberNo() {
        return memberNo;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
