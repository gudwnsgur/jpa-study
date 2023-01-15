package com.study.querydsl.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

/**
 * @author Joonhyuck Hyoung
 */
@Entity
class Team(
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    val id: Long = 0L,
    val name: String,

    @OneToMany(mappedBy = "team")
    val members: MutableList<Member> = mutableListOf()
) {
    override fun toString(): String {
        return "Team(id=${id}, name=${name})"
    }

    companion object {
        fun of(name: String) = Team(name = name)
    }
}
