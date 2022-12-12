package jpabook.springdatajpa.domain

import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
@Entity
data class Member(
    @Id @GeneratedValue
    val id: Long = 0L,
    val username: String,
) {
    companion object {
        fun create(username: String) = Member(
            username = username
        )
    }
}
