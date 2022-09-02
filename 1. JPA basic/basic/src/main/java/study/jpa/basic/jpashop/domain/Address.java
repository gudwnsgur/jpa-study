package study.jpa.basic.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Joonhyuck Hyoung
 */
@Embeddable
public class Address {

    @Column(length = 10)
    private String cicy;

    @Column(length = 30)
    private String street;

    @Column(length = 5)
    private String zipcode;

    // Address 를 사용하는 곳에서 같이 사용할 수 있는 메소드
    public String fullAddress(){
        return getCicy() +" "+ getStreet() +" ("+getZipcode()+")";
    }

    public String getCicy() {
        return cicy;
    }

    private void setCicy(String cicy) {
        this.cicy = cicy;
    }

    public String getStreet() {
        return street;
    }

    private void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    private void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
