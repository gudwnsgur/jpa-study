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

    fun findAll(): List<Member> {
        return em.createQuery("select m from Member m", Member::class.java).resultList
    }

    fun findById(id: Long): Member? {
        return em.find(Member::class.java, id)
    }

    fun count(): Long {
        return em.createQuery("select count(m) from Member m", Long::class.java).singleResult
    }

    // 이름과 나이 기준으로 회원 조회
    fun findByUserNameAndAgeGreaterThan(username: String, age: Int): List<Member> {
        return em.createQuery("select m from Member m where m.username = :username and m.age > :age",
            Member::class.java)
            .setParameter("username", username)
            .setParameter("age", age)
            .resultList
    }

    fun findByPage(age: Int, offset: Int, limit: Int): List<Member> {
        return em.createQuery("select m from Member m where m.age = :age order by m.username desc", Member::class.java)
            .setParameter("age", age)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun bulkAgePlus(age: Int): Int {
        return em.createQuery("update Member m set m.age = m.age + 1 where m.age >= :age", Member::class.java)
            .setParameter("age", age)
            .executeUpdate()
    }
}
