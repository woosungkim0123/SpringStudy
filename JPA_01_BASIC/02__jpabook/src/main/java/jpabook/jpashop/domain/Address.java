package jpabook.jpashop.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public String getCity() {
        return city;
    }

    private void setCity(String city) {
        this.city = city;
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
    // use getter during code generate 사용하는게좋음
    // getter를 호출함. 선택안하면 필드에 직접접근. 필드 직접접근 문제는 프록시일때 계산이 안됨
    // 프록시일떄도 getter를 호출해야 진짜 객체에게 가거나 함
    // jpa에서는 프록시떄문에 고려를해서 항상 메서드를 통해서 값을 get하도록 구현하는게 좋음
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) && Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getZipcode(), address.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getZipcode());
    }
}
