package jpabook.jpashop.api

import jpabook.jpashop.domain.*
import jpabook.jpashop.service.*
import org.springframework.web.bind.annotation.*

/**
 * @author Joonhyuck Hyoung
 */

@RestController
class MemberApiController(
    private val memberService: MemberService,
) {
    /**
     * 등록 API
     */

    /**
     * V1 엔티티를 Request Body에 직접 매핑
     * - 요청 값으로 Member 엔티티를 직접 받는다.
     * 문제점
     * - 엔티티에 프레젠테이션 계층을 위한 로직이 추가된다.
     * - 엔티티에 API 검증을 위한 로직이 들어간다. (@NotEmpty 등등)
     * - 실무에서는 회원 엔티티를 위한 API가 다양하게 만들어지는데, 한 엔티티에 각각의 API를 위한 모든 요청 요구사항을 담기는 어렵다.
     * - 엔티티가 변경되면 API 스펙이 변한다.
     * 결론
     * - API 요청 스펙에 맞추어 별도의 DTO를 파라미터로 받는다.
     */
    @PostMapping("/api/v1/members")
    fun saveMemberV1(@RequestBody member: Member): CreateMemberResponse {
        val id = memberService.join(member)
        return CreateMemberResponse(id)
    }

    /**
     * V2 엔티티 대신에 DTO를 RequestBody에 매핑
     * - 요청 값으로 Member 엔티티 대신에 별도의 DTO를 받는다.
     * - 엔티티와 프레젠테이션 계층을 위한 로직을 분리할 수 있다.
     * - 엔티티가 변해도 API 스펙이 변하지 않는다.
     */
    @PostMapping("/api/v2/members")
    fun saveMemberV2(@RequestBody request: CreateMemberRequest): CreateMemberResponse {
        val member = Member(name = request.name)
        val id = memberService.join(member)
        return CreateMemberResponse(id)
    }

    /**
     * 수정 API
     */
    @PatchMapping("/api/v2/member/{id}")
    fun updateMemberV2(
        @PathVariable("id") id: Long,
        @RequestBody request: UpdateMemberRequest,
    ): UpdateMemberResponse {
        memberService.update(id, request.name)

        return memberService.findOne(id)?.let { member ->
            UpdateMemberResponse(
                member.id,
                member.name
            )
        } ?: throw IllegalStateException("회원 없음")
    }

    /**
     * 조회 API
     */
    /**
     * 회원조회 V1: 응답 값으로 엔티티를 직접 외부에 노출
     * 문제점
     * - 엔티티에 프레젠테이션 계층을 위한 로직이 추가된다.
     * - 엔티티의 모든 값이 노출된다.
     * - 응답 스펙을 맞추기 위해 로직이 추가된다. (@JsonIgnore, 별도의 뷰 로직 등등)
     * - 실무에서는 같은 엔티티에 대해 API가 용도에 따라 다양하게 만들어지는데, 한 엔티티에 각각의 API를 위한 프레젠테이션 응답 로직을 담기는 어렵다.
     * - 엔티티가 변경되면 API 스펙이 변한다.
     * - 추가로 컬렉션을 직접 반환하면 항후 API 스펙을 변경하기 어렵다.
     * 결론
     * - API 응답 스펙에 맞추어 별도의 DTO를 반환한다.
     */
    @GetMapping("/api/v1/members")
    fun membersV1(): List<Member> {
        return memberService.findMembers()
    }

    /**
     * 회원조회 V2: 응답 값으로 엔티티가 아닌 별도의 DTO 사용
     */
    @GetMapping("/api/v2/members")
    fun membersV2(): List<MemberDto> {
        return memberService.findMembers().map {
            MemberDto(name = it.name)
        }
    }
}

data class CreateMemberRequest(
    val name: String,
)

data class CreateMemberResponse(
    val id: Long,
)

data class UpdateMemberRequest(
    val name: String,
)

data class UpdateMemberResponse(
    val id: Long,
    val name: String,
)

data class MemberDto(
    val name: String,
)
