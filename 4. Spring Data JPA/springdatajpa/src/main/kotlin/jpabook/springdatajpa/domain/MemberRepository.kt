package jpabook.springdatajpa.domain

import jpabook.springdatajpa.dto.*
import org.springframework.data.jpa.repository.*
import org.springframework.data.repository.query.*

/**
 * @author Joonhyuck Hyoung
 */
interface MemberRepository : JpaRepository<Member, Long> {
    fun findByUsernameAndAgeGreaterThan(username: String, age: Int): List<Member>

    @Query(name = "Member.findByUsername") // 생략 가능
    fun findByUsername(@Param("username") username: String): List<Member>

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
}
