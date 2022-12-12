package jpabook.springdatajpa.domain

import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*
import org.springframework.test.annotation.*
import org.springframework.transaction.annotation.*
import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
@SpringBootTest
@Transactional
class MemberTests @Autowired constructor(
    private val em: EntityManager,
) {
    @Test
    @Transactional
    @Rollback(false)
    fun testEntity() {
        val teamA = Team(name = "teamA")
        val teamB = Team(name = "teamB")
        em.persist(teamA)
        em.persist(teamB)

        em.persist(Member(username = "member1", age = 10, team = teamA))
        em.persist(Member(username = "member2", age = 20, team = teamA))
        em.persist(Member(username = "member3", age = 30, team = teamB))
        em.persist(Member(username = "member4", age = 40, team = teamB))
        em.persist(Member(username = "member5", age = 50, team = teamB))
    }
}
