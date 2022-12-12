package jpabook.springdatajpa.domain

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*
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
        val member = Member.create("memberA")
        val saveMember = memberRepository.save(member)

        val findMember = memberRepository.findById(saveMember.id).get()

        assertEquals(findMember.id, member.id)
        assertEquals(findMember.username, member.username)

        assertEquals(findMember, member)
    }
}
