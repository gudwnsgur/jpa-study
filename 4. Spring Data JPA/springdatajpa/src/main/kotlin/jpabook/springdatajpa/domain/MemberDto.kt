package jpabook.springdatajpa.domain

import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
data class MemberDto(
    val id: Long = 0L,
    val username: String,
    val age: Int,
    var team: Team? = null,
) {
    companion object {
        fun by(member: Member) = MemberDto(
            id = member.id,
            username = member.username,
            age = member.age,
            team = member.team
        )
    }
}
