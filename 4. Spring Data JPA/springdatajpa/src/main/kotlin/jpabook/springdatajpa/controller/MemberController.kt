package jpabook.springdatajpa.controller

import jpabook.springdatajpa.domain.*
import org.springframework.data.domain.*
import org.springframework.data.web.*
import org.springframework.web.bind.annotation.*
import javax.annotation.*

/**
 * @author Joonhyuck Hyoung
 */
@RestController
class MemberController(
    private val memberJpaRepository: MemberJpaRepository,
    private val memberRepository: MemberRepository,
    private val teamRepository: TeamRepository,
) {
    /**
     * 파라미터로 Pageable을 받을 수 있다.
     * 컨트롤러에 파라미터로 Pageable이 있으면 PageRequest라는 객체를 생성해서 값을 채워서 request로 들어간다.
     *
     *
     * http://localhost:8080/members
     * http://localhost:8080/members?page=0&size=10&sort=username,desc
     */
    @GetMapping("members")
    fun list(
        @PageableDefault(size = 12, sort = ["username"], direction = Sort.Direction.DESC) pageable: Pageable,
    ): Page<MemberDto> {
        return memberRepository.findAll(pageable).map { MemberDto.by(it) }
    }

    @PostConstruct
    fun init() {
        (1..100).forEach { idx ->
            memberRepository.save(Member(username = "member${idx}", age = idx))
        }
    }
}
