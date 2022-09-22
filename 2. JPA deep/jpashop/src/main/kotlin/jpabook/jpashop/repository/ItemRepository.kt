package jpabook.jpashop.repository

import jpabook.jpashop.domain.item.*
import org.springframework.stereotype.Repository
import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
@Repository
class ItemRepository(
    // 엔티티 메니저( EntityManager ) 주입
    @PersistenceContext
    private val em: EntityManager
) {
    /**
     * id가 없으면 신규 : persist()
     * id가 있으면 이미 있는 item 을 수정하는것으로 보고 merge()
     */
    fun save(item: Item) {
        when(item.id) {
            null -> em.persist(item)
            else ->em.merge(item)
        }
    }

    fun findOne(id: Long): Item? = em.find(Item::class.java, id)

    fun findAll(): List<Item> = em.createQuery("select i from Item i", Item::class.java).resultList
}
