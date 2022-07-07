## PK 매핑

### 매핑 방식

#### 1.@GeneratedValue(strategy = GenerationType.IDENTITY) 
- 기본키 생성을 DB에 위임

#### 2. @GeneratedValue(strategy = GenerationType.SEQUENCE)
- mysql에선 안쓴다(유일한 값을 순서대로 생성하는 특별한 DB Object)

#### 3. @GeneratedValue(strategy = GenerationType.TABLE)
- 시퀀스 테이블을 만들어서 거기에 따라 키 매핑 (안쓴다)
- 그냥 첫번째꺼 쓰자

----

### id에 Long을 쓰는 이유
  * 생각보다 Integer랑 Long이랑 두배정도 차이가 나겠지만
  * 전체 application으로 봤을때 Integer -> Long은 영향이 거의 없다.
  * 10억이 넘어갈때 type을 바꾸는게 더 골치아프다.

---

### id RULE!
1. NOT NULL NO CHANGE
2. 비즈니스적인 자연값을 키로 끌고오는건 좋지 않다.
3. Long + auto_increment / sequence / uuid

- 실제 사례
  - 나라에서 주민번호를 보관하면 안된다해서 날려야 하는데? 이게 뭐람 주민번호를 pk로 쓰고있었음
  - 회원 테이블을 가져다쓰는 다른 서비스도 ㅠㅠ
  
---




