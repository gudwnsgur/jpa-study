package study.jpa.basic.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Joonhyuck Hyoung
 */
@Entity(name = "jpashop_member")
@Table(name = "jpashop_member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String name;
    private String city;
    private String street;
    @Column(name = "zip_code")
    private String zipCode;

    @Embedded // Address 에서 @Embeddable 추가시 생략가능
    private Address address;

    // 실무에서 회원 A가 어떤 주문을 했는지 알 필요가 많지 않지만 예제니까 양방향 매핑 ㄱㄱ
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Long getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
