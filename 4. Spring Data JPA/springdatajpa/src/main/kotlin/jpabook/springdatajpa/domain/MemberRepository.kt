package jpabook.springdatajpa.domain

import jpabook.springdatajpa.dto.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*
import org.springframework.data.repository.query.*

/**
 * @author Joonhyuck Hyoung
 */
interface MemberRepository : JpaRepository<Member, Long> {
    fun findByUsernameAndAgeGreaterThan(username: String, age: Int): List<Member>

//    @Query(name = "Member.findByUsername") // 생략 가능
//    fun findByUsername(@Param("username") username: String): List<Member>

    @Query("select m from Member m where m.username= :username and m.age = :age")
    fun findUser(@Param("username") username: String, @Param("age") age: Int): List<Member>

    // 단순 값 하나 조회
    @Query("select m.username from Member m")
    fun findUserNameList(): List<String>

    // DTO로 직접 조회 (DTO가 있어야 함) 하지만 이후에 queryDSL 쓰면 이거보다 더 편함 ㅎㅎ
    @Query("select new jpabook.springdatajpa.dto.MemberDto(m.id, m.username, t.name) " +
        "from Member m join m.team t")
    fun fundMemberDto(): List<MemberDto>

    @Query("select m from Member m where m.username in :names")
    fun findByNames(@Param("names") names: List<String>)

    // pageAble 인터페이스 구현체를 넘기면 되는데 보통 PageRequest를 쓴다
    fun findByAge(age: Int, pageable: Pageable): Page<Member>

    @Modifying
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    fun bulkAgePlus(@Param("age") age: Int): Int

    // 연관된 엔티티를 한번에 가져오려면??? FETCH!!!
    @Query("select m from Member m left join fetch m.team")
    fun findMemberFetchJoin(): List<Member>

    // JPQL 없이도 페치조인을 사용할 수 있다!

    // 1. 공통메서드를 오버라이드하는 방식
    @EntityGraph(attributePaths = {"team"})
    override fun findAll(): List<Member>

    // 2. JPQL + 엔티티그래프
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    fun findMemberEntityGraph(): List<Member>

    // 3. 메서드명으로
    @EntityGraph(attributePaths = {"team"})
    fun findByUsername(username: String): List<Member>

    // fetch 조인의 간편 버전이라 생각하면 된다.
    // left outer join 사용
}
