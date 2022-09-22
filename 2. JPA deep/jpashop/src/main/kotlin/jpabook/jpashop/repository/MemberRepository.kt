package jpabook.jpashop.repository

import jpabook.jpashop.domain.*
import org.springframework.stereotype.*
import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
// 스프링 빈으로 등록
@Repository
class MemberRepository(
    // 엔티티 메니저( EntityManager ) 주입
    @PersistenceContext
    private val em: EntityManager
) {
    fun save(member: Member) {
        em.persist(member)
    }

    fun findOne(id: Long): Member? = em.find(Member::class.java, id)

    fun findAll(): List<Member> = em.createQuery("select m from Member m", Member::class.java).resultList

    fun findByName(name: String): List<Member> = em.createQuery(
        "select m from Member m where m.name = :name",
        Member::class.java
    ).setParameter("name", name).resultList
}
