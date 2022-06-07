## flush
#### 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영
- 영속성 컨텍스트와 DB의 싱크를 맞춰주는 역할
- insert, update, delete 쿼리를 날린다.


#### flush가 발생하면? (transaction이 commit되면)
- 변경 감지 (dirty checking)
- 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
- 쓰기 지연 SQL저장소의 쿼리를 (한번에) DB에 전송(insert, update, delete)

<br>

---

### 영속성 컨텍스트를 플러시 하는 방법

1. `em.flush()` : 직접 호출 (잘 안쓸거임)
2. `transaction의 commit` : 자동 호출
3. `JPQL 쿼리 실행` : 자동 호출

<br>

--- 
 
### JPQL 쿼리 실행시 플러시가 자동으로 호출되는 이유는?

```java
Member member = new Member(15L, "형준혁");
em.persist(member); 

Member cur = em.find(Member.class, 15L);
System.out.println(cur.getName()); // 형준혁
```
- `em.persist(member)`를 통해 1차 캐시에 `Member(15L, "형준혁")` 저장
- `em.find()`는 1차 캐시부터 확인하기 때문에 결과를 얻을 수 있다.

<br>


```java

em.persist(memberA);
em.persist(memberB);
em.persist(memberC);

// JPQL
query = em.createQuery("select m from Member m", Member.class);
List<Member> members = query.getResultList();
```

- JPQL은 영속성 컨텍스트를 들리지 않고 디비로 직접 쏘기 때문에 이전 반영사항이 추가되지 않는다.
- 따라서 JPQL query를 쏘기 전에 다 flush 해버리고 쿼리문을 쏜다.

<br>

---
### 플러시 모드 옵션
`em.setFlushMode(FlushModeType.COMMIT)`

- `FlushModeType.AUTO` : 커밋이나 쿼리를 실행할때만 flush

- `FlushModeType.COMMIT` : commit에서만 flush

<br>

- 보통 JPQL을 실행하기 전의 `persist()`, `remove()` 등을 굳이 반영할 필요가 없을 때, <br> flush를 해봐야 이득이 없을 때 `FlushModeType.COMMIT`를 쓰지만 <br> 그냥 웬만하면 `AUTO` 쓰자
