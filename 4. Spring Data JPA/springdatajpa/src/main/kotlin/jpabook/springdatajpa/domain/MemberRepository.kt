package jpabook.springdatajpa.domain

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
}
