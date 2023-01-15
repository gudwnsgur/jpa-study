package com.study.querydsl.domain

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

/**
 * @author Joonhyuck Hyoung
 */

@SpringBootTest
@Transactional
class EntityTests @Autowired constructor(
    private val em: EntityManager
){
    @Test
    fun `엔티티 테스트`() {
        val teamA = Team(name = "teamA")
        val teamB = Team(name = "teamB")
        em.persist(teamA)
        em.persist(teamB)

        val member1 = Member(username = "member1", age = 10, team = teamA)
        val member2 = Member(username = "member2", age = 20, team = teamA)
        val member3 = Member(username = "member3", age = 30, team = teamB)
        val member4 = Member(username = "member4", age = 40, team = teamB)

        em.persist(member1)
        em.persist(member2)
        em.persist(member3)
        em.persist(member4)

        // 초기화
        em.flush()
        em.clear()

        val members = em.createQuery("select m from Member m", Member::class.java).resultList

        members.forEach { member ->
            println("member : $member")
            println("team : ${member.team}")
        }

    }
}
