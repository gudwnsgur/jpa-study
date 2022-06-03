## 소개

- 과거에는 데이터베이스에 데이터를 저장하려면 복잡한 jdbc api & sql을 직접 작성해야 했다.
- jdbcTemplate, myBatis가 생기면서 편해졌지만 sql을 개발자가 직접 작성해야 하는 문제점이 생겼다.
- JPA를 사용하면 sql조차도 작성할 필요가 없어졌다.

```java
public class MemberDAO {
    @PersistenceContext
    EntityManager em;
    
    public void save(Member member) {
        jpa.persist(member);
    }
    public Member findOne(Long id) {
        return jpa.find(Member.class, id);
    }
}
```


- jpa가 개발자 대신 sql 생성하여 객체를 저장, 조회
- SQL을 한땀한땀 작성하는건 개발 생산성 측면에서 뒤떨어진다.
- 개발 속도 & 유지보수 측면에서 유리하다.

<br>

### JPA가 실무에서 사용하기 어려운 이유
- 처음 jpa, Spring Data jpa를 만나면
  - 섣부르게 튜토리얼만 보고 실무에 들어가면 큰일납니다...
  - 객체랑 테이블을 잘 설계하고 매핑하는게 제일 중요한데 이걸 잘 모르기 때문에
- 실무의 수십 개 이상의 객체와 테이블을 사용하게 되면 힘들 수 밖에 없음

<br>

### 목표1 : 객체와 테이블 설계 매핑
- 객체와 테이블을 제대로 설계하고 매핑하는 방법
- 기본 키 & 외래 키 매핑
- 1:N, N:1, 1:1, N:M 매핑
- 실무 노하우 + 성능

<br>

### 목표2 : JPA 내부 동작 방식 이해
- JPA 내부 동작 방식을 이해하자
- JPA가 어떤 SQL을 만들어내는지 이해하자
- JPA가 어떤 SQL을 실행하는지 이해하자

<br>

### JPA 적용 사례
- 우아한형제들, 쿠팡, 카카오, 네이버, NHN
- 조 단위의 거래금액이 발생하는 다양한 서비스에서 사용 & 검증
- 최신 스프링 예제들만 봐도 JPA
- 이제 자바 개발자에게 JPA는 기본 기술이다.
