package jpabook.springdatajpa.domain

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*
import org.springframework.transaction.annotation.*
import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
@SpringBootTest
@Transactional
class MemberJpaRepositoryTest @Autowired constructor(
    private val em: EntityManager,
) {
    @Autowired
    private lateinit var memberJpaRepository: MemberJpaRepository

    @BeforeEach
    fun beforeEach() {
        memberJpaRepository = MemberJpaRepository(em)
    }

    @Test
    fun testMember() {
        val member = Member.create("memberA", 20)
        val saveMember = memberJpaRepository.save(member)

        val findMember = memberJpaRepository.find(saveMember.id)

        assertEquals(findMember.id, member.id)
        assertEquals(findMember.username, member.username)

        assertEquals(findMember, member)
    }

    @Test
    fun findByUserNameAndAgeGreaterThan() {
        memberJpaRepository.save(Member.create("AAA", 10))
        memberJpaRepository.save(Member.create("AAA", 20))

        val result = memberJpaRepository.findByUserNameAndAgeGreaterThan("AAA", 15)
        assertEquals("AAA", result[0].username)
        assertEquals(20, result[0].age)
        assertEquals(1, result.size)
    }
}
