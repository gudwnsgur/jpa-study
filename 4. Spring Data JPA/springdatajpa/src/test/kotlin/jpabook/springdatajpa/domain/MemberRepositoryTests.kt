package jpabook.springdatajpa.domain

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*
import org.springframework.data.domain.*
import org.springframework.transaction.annotation.*

/**
 * @author Joonhyuck Hyoung
 */
@SpringBootTest
@Transactional
class MemberRepositoryTest @Autowired constructor(
    private val memberRepository: MemberRepository,
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
}
