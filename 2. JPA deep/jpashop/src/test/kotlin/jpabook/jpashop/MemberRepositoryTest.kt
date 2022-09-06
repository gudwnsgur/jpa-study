package jpabook.jpashop

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
internal class MemberRepositoryTest @Autowired constructor(
    private val memberRepository: MemberRepository,
) {
    @Test
    /**
     * entityManager를 통한 모든 데이터 변경은 항상 트랜잭션 안에서 이루어져야 한다.
     * @Transactional : 테스트에 있으면 테스트가 끝난 후 롤백
     */
    @Transactional
//    @Rollback(value = false) 롤백 안하고 싶을 때 사용
    fun testMember() {
        // given
        val member = Member(userName = "형준혁")

        // when
        val savedId = memberRepository.save(member)
        val findMember = memberRepository.find(savedId)

        // then
        Assertions.assertEquals(savedId, findMember.id)
        Assertions.assertEquals(member.userName, findMember.userName)
        // Question : 아래의 결과는?
        Assertions.assertEquals(findMember, member)
    }
    /***
     * 데이터베이스 결과
     * SELECT * FROM MEMBER;
     *
     *  ID | USER_NAME
     * (행 없음, 4 ms)
     */


    @Test
    fun save() {

    }

    @Test
    fun find() {

    }
}