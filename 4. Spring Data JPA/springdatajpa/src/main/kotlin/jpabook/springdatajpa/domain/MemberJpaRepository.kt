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

    fun findAll() : List<Member> {
        return em.createQuery("select m from Member m", Member::class.java).resultList
    }

    fun findById(id: Long): Member? {
        return em.find(Member::class.java, id)
    }

    fun count(): Long {
        return em.createQuery("select count(m) from Member m", Long::class.java).singleResult
    }
}
