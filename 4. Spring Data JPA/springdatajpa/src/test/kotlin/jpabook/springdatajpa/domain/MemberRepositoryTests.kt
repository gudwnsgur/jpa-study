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

    @Test
    fun testMember() {
        val member = Member.create("memberA", 20)
        val saveMember = memberRepository.save(member)

        val findMember = memberRepository.findById(saveMember.id).get()

        assertEquals(findMember.id, member.id)
        assertEquals(findMember.username, member.username)

        assertEquals(findMember, member)
    }

    @Test
    fun basicCRUD() {
        val member1 = Member.create("member1", 20)
        val member2 = Member.create("member2", 25)
        memberRepository.save(member1)
        memberRepository.save(member2)

        // 단건 조회 검증
        val findMember1 = memberRepository.findById(member1.id).get()
        val findMember2 = memberRepository.findById(member2.id).get()
        assertEquals(member1, findMember1)
        assertEquals(member2, findMember2)

        // 리스트 조회 검증
        val all = memberRepository.findAll()
        assertEquals(2, all.size)

        // 카운트 검증
        val count = memberRepository.count()
        assertEquals(2, count)

        // 삭제 검증
        memberRepository.delete(member1)
        memberRepository.delete(member2)
        val deleteCount = memberRepository.count()
        assertEquals(0, deleteCount)
    }

    @Test
    fun findByUserNameAndAgeGreaterThan() {
        memberRepository.save(Member.create("AAA", 10))
        memberRepository.save(Member.create("AAA", 20))

        val result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15)
        assertEquals("AAA", result[0].username)
        assertEquals(20, result[0].age)
        assertEquals(1, result.size)
    }

    @Test
    fun page() {
        // given
        memberRepository.save(Member(username = "memberA", age = 10))
        memberRepository.save(Member(username = "memberB", age = 20))
        memberRepository.save(Member(username = "memberC", age = 30))
        memberRepository.save(Member(username = "memberD", age = 40))
        memberRepository.save(Member(username = "memberE", age = 50))

        // when
        val pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"))
        val page: Page<Member> = memberRepository.findByAge(age = 10, pageable = pageRequest)

        val content = page.content
        println("""
            ======== paging ========
            size : ${content.size}
            total elements : ${page.totalElements}
            page number : ${page.number}
            total pages : ${page.totalPages}
            첫 번째 항목인가? : ${page.isFirst}
            다음 페이지가 있는가? :${page.hasNext()}
            ======== paging ========
        """.trimIndent())
    }

    @Test
    fun bulkUpdate() {
        // given
        memberRepository.save(Member(username = "member1", age = 10))
        memberRepository.save(Member(username = "member2", age = 19))
        memberRepository.save(Member(username = "member3", age = 20))
        memberRepository.save(Member(username = "member4", age = 21))
        memberRepository.save(Member(username = "member5", age = 40))

        // when
        val resultCount = memberRepository.bulkAgePlus(20)

        // then
        assertEquals(3, resultCount)
    }

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

        // when
        val members = memberRepository.findAll()

        // then
        members.forEach { member ->
            println("지연로딩 상태 확인 : ${Hibernate.isInitialized(member.team)}")
            member.team?.name
            println("지연로딩 상태 확인 : ${Hibernate.isInitialized(member.team)}")
        }
    }
}
