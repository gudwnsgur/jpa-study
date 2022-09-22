package jpabook.jpashop.service

import jpabook.jpashop.domain.*
import jpabook.jpashop.repository.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.*

/**
 * @author Joonhyuck Hyoung
 */
@SpringBootTest // 스프링 부트 띄우고 테스트(이게 없으면 @Autowired 다 실패)
internal class MemberServiceTest(
    private val memberService: MemberService,
    private val memberRepository: MemberRepository,
){
    /*
    반복 가능한 테스트 지원
    각각의 테스트를 실행할 때마다 트랜잭션을 시작하고 테스트가 끝나면 트랜잭션을 강제로 롤백
     */
    @Test
    fun `회원가입 테스트`() {
        // given
        val member = Member(name = "형준혁")

        // when
        val saveId = memberService.join(member)

        // then
        assertEquals(member, memberRepository.findOne(saveId))
    }

    @Test
    fun `중복 회원 예외 발생 테스트`() {
        // given
        val member1 = Member(name = "형준혁")
        val member2 = Member(name = "형준혁")

        // when
        memberService.join(member1)

        // then
        assertThrows<IllegalStateException> {
            memberService.join(member2)
        }
    }
}
