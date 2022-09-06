package jpabook.jpashop

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class MemberRepository {
    @PersistenceContext
    lateinit var em: EntityManager

    // 명령과 쿼리를 구분하라
    // 저장을 하고 나면 가급적 side effect를 일으킬 수 있는 커맨드성 데이터(member)를 return 하지 말자
    fun save(member: Member): Long {
        em.persist(member)
        return member.id
    }

    fun find(id: Long): Member = em.find(Member::class.java, id)
}