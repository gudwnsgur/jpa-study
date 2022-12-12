package jpabook.springdatajpa.domain

import org.springframework.stereotype.*
import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
@Repository
class MemberJpaRepository(
    private val em: EntityManager,
) {
    fun save(member: Member): Member {
        em.persist(member)
        return member
    }

    fun find(id: Long): Member {
        return em.find(Member::class.java, id)
    }
}
