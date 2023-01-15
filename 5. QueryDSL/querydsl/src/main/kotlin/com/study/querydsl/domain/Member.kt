package com.study.querydsl.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
@Entity
class Member(
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    val id: Long = 0L,
    val username: String,
    val age: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    var team: Team? = null,
) {
    fun changeTeam(team: Team?) {
        this.team = team
        team?.members?.add(this)
    }

    override fun toString(): String {
        return "Member(id=${id} username=${username}, age=${age})"
    }

    companion object {
        fun of(username: String) = Member(username = username)

        fun of(username: String, age: Int) = Member(
            username = username,
            age = age
        )
    }
}

