package jpabook.jpashop.repository

import jpabook.jpashop.domain.*
import org.springframework.stereotype.Repository
import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
@Repository
class OrderRepository(
    // 엔티티 메니저( EntityManager ) 주입
    @PersistenceContext
    private val em: EntityManager
) {
    fun save(order: Order) {
        em.persist(order)
    }

    fun findOne(id: Long): Order? = em.find(Order::class.java, id)

//    fun findAll(orderSearch: OrderSearch): List<Order> {}
}
