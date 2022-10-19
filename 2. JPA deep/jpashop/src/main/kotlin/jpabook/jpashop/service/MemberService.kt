package jpabook.jpashop.service

import jpabook.jpashop.domain.*
import jpabook.jpashop.repository.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*

/**
 * @author Joonhyuck Hyoung
 */
@Service
/*
데이터의 변경이 없는 읽기 전용 메서드에 사용
영속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용하는것이 좋다)
 */
@Transactional(readOnly = true)
class MemberService(
    // 코틀린의 생성자 주입
    private val memberRepository: MemberRepository,
) {
    /**
     * 회원가입
     */
    @Transactional // readOnly를 false로
    fun join(member: Member): Long {
        validateDuplicateMember(member)
        memberRepository.save(member)
        return member.id
    }

    /**
     * 전체 회원 조회
     */
    fun findMembers() = memberRepository.findAll()

    /**
     * 회원 한명 조회
     */
    fun findOne(memberId: Long) = memberRepository.findOne(memberId)

    private fun validateDuplicateMember(member: Member) {
        if(memberRepository.findByName(member.name).isNotEmpty())
            throw IllegalStateException("이미 존재하는 회원입니다.")
    }

    @Transactional
    fun update(id: Long, name: String) {
        memberRepository.findOne(id)?.apply {
            this.name = name
        }

        /** in java **/
        // Member member = memberRepository.findOne(id);
        // member.setName(name);
    }
}
