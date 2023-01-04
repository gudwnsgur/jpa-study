package jpabook.springdatajpa.domain

import org.hibernate.Hibernate
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*
import org.springframework.data.domain.*
import org.springframework.transaction.annotation.*
import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
@SpringBootTest
@Transactional
class MemberRepositoryTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val teamRepository: TeamRepository,
    private val em: EntityManager,
) {

    // @EntityGraph
    // 연관된 엔티티들을 SQL 한번에 조회하는 방법
    // member - team은 지연로딩 관계. 따라서 team의 데이터를 다음처럼 조회할 때 마다 쿼리가 실행된다. (N+1 문제 발생)
    @Test
    fun findMemberLazy() {
        // given
        // member1 -> teamA
        val teamA = Team(name = "teamA")
        teamRepository.save(teamA)
        memberRepository.save(Member(username = "member1", age = 10, team = teamA))

        // member2 -> teamB
        val teamB = Team(name = "teamB")
        teamRepository.save(teamB)
        memberRepository.save(Member(username = "member2", age = 20, team = teamB))

        em.flush()
        em.clear()

        // 1. 기본 findAll test
        // 2. lazy loading test
        // 3. entity graph test
        // when
        println("========================================")
        val members = memberRepository.findAll()
        println("========================================")
        println(members[0].age)
        println("========================================")
        println(members[0].team?.javaClass)
        println("========================================")
        println(members[0].team?.name)
        println("========================================")

        // then
//        members.forEach { member ->
//            println("지연로딩 상태 확인 : ${Hibernate.isInitialized(member.team)}")
//            member.team?.name
//            println("지연로딩 상태 확인 : ${Hibernate.isInitialized(member.team)}")
//        }
    }
}
