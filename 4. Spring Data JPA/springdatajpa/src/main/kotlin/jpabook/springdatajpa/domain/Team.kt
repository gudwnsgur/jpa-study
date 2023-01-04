package jpabook.springdatajpa.domain

import net.minidev.json.annotate.JsonIgnore
import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
@Entity
class Team(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String,

    @OneToMany(mappedBy = "team")
    val members: MutableList<Member> = mutableListOf()
) {
    companion object {
        fun create(name: String) = Team(name = name)
    }
}
