## JPA
- Java Persistence API
- 자바 진영의 ORM 기술 표준

---

### ORM
- Object-Relational Mapping (객체 관계 매핑)
- 객체는 객체대로 설계 & RDB는 RDB대로 설계하면 ORM이 매핑해준다.
- JPA는 java application & JDBC 사이에서 동작
  - JPA가 jdbc api를 사용하여 DB에 sql을 쏘고 결과를 받는 형식

---

### JPA 역사

- 과거에는 `EJB` 라는 자바 표준 엔티티 빈 존재 : 개구렸음
- 열받은 개발자가 `Hibernate`라는 오픈소스를 만들었음
- 반성한 자바가 이 개발자를 데려와서 `Hibernate`를 복사붙여넣기 한 수준의 `JPA (자바표준)`을 만들었음

`JPA는 인터페이스의 모음이고 구현체는 하이버네이트`

---

## JPA 장점

### 1. 생산성

- 저장 : `jpa.persist(member)`
- 조회 : `Member member = jpa.find(id)`
- 수정 : `member.setName("홍길동")`
- 삭제 : `jpa.remove(member)`

### 2. 유지보수

- 기존에서 SQL문을 한땀한땀 수정해야 했는데 이런거 안해도 된다.
- 컬럼을 추가해야 하면 JPA 필드만 수정하면 된다.

### 3. JPA와 패러다임의 불일치 해결

- 상속 (Item, Book)
  - `jpa.persist(book)` 하면 `insert item`, `insert book` 다 해준다.
  - `Book book = jpa.find(Book.class, id)` 하면 알아서 Item, Book 테이블 Join해서 찾아준다.
- 연관관계
  - 저장
    - Member에 Team을 세팅하고 그 Member를 저장하고 싶을 때
    - `member.setTeam(team)`
    - `jpa.persist(member)`
  - 탐색
    - member를 찾으면 그 안의 team도 찾을 수 있다. 
    - `Member memer = jpa.find(Member.class, id)`
    - `Team team = member.getTeam()` 해도 

### 4. 성능 최적화

- 중간계층이 생기면 할수 있는게 있다 (모아서 쏘는 버퍼링, 읽을때 캐싱)
- 1차 캐시와 동일성 보장
- 트랜잭션을 지원하는 쓰기 지원
- 지연 로딩 지원
