package jpabook.springdatajpa.domain

import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
@Entity
data class Member(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val username: String,
    val age: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    var team: Team? = null,
) {
    // 연관관계 편의 메서드
    fun changeTeam(team: Team) {
        this.team = team
        team.members.add(this)
    }

    companion object {
        fun create(username: String, age: Int) = Member(
            username = username,
            age = age,
        )
        fun create(username: String, age: Int, team: Team) = Member(
            username = username,
            age = age,
            team = team
        )
    }
}
