package jpabook.jpashop.repository

import jpabook.jpashop.service.*
import org.springframework.stereotype.*
import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
@Repository
class OrderSimpleQueryRepository(
    @PersistenceContext
    private val em: EntityManager,
) {
    fun findOrders(): List<OrderSimpleQueryCommand> {
        return em.createQuery(
            "select new jpabook.jpashop.service.OrderSimpleQueryCommand(o.id, m.name,o.orderDate, o.status, d.address)" +
                " from Order o" +
                " join o.member m" +
                " join o.delivery d", OrderSimpleQueryCommand::class.java).resultList
    }
}
