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
@Entity(name = "jpashop_delivery")
@Table(name = "jpashop_delivery")
public class Delivery {
    @Id @GeneratedValue
    private Long no;

    private String city;
    private String street;
    private String zipCode;
    private DeliveryStatus status;

    // 양방향
    @OneToOne(mappedBy = "delivery")
    private Order order;
}