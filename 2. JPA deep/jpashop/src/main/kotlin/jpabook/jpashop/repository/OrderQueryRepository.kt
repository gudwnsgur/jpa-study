package jpabook.jpashop.repository

import org.springframework.stereotype.*
import java.util.stream.*
import javax.persistence.*

@Repository
class OrderQueryRepository(
    // 엔티티 메니저( EntityManager ) 주입
    @PersistenceContext
    private val em: EntityManager,
) {
    /**
     * 컬렉션은 별도 조회
     * Query : root 1 + 컬렉션 N
     * 단건 조회에 많이 사용
     */
    fun findOrderQueryDtos(): List<OrderQueryDto> {
        // root 조회(toOne 코드를 모두 한번에 조회)
        val result = findOrders() // Query 1

        // 루프를 돌면서 컬렉션 추가(추가 쿼리 실행) // Query N
        return result.onEach { order ->
            order.orderItems = findOrderItems(order.orderId)
        }
    }

    // 1:N 관계(컬렉션)를 제외한 나머지를 한번에 조회
    // 이거 하면서 영한쌤이 한말
    // 하.... 이래서 그... 쿼리디에셀 쓰시면은 이런거 안해도 되거등용.... 다 바로 할수있거든용...
    private fun findOrders(): List<OrderQueryDto> {
        return em.createQuery(
            "select new jpabook.jpashop.repository.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                " from Order o" +
                " join o.member m" +
                " join o.delivery d", OrderQueryDto::class.java
        ).resultList
    }

    // 1:N 관계인 orderItems 조회
    private fun findOrderItems(orderId: Long): List<OrderItemQueryDto> {
        return em.createQuery(
            "select new jpabook.jpashop.repository.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                " from OrderItem oi" +
                " join oi.item i" +
                " where oi.order.id = : orderId", OrderItemQueryDto::class.java
        ).setParameter("orderId", orderId).resultList
    }

    /**
     * 최적화
     * Query : root 1번, 컬렉션 1번
     * 데이터를 한꺼번에 처리할 때 많이 사용하는 방식
     */
    fun findAllByDto_optimization(): List<OrderQueryDto> {
        //루트 조회(toOne 코드를 모두 한번에 조회)
        val result = findOrders() // 여긴 똑같음 Query 1

        // orderItem 컬렉션을 map으로 // in query를 내가 직접 명시하겠다! Query 1
        val orderItemMap: Map<Long, List<OrderItemQueryDto>> = findOrderItemMap(toOrderIds(result))

        // 루프를 돌면서 컬렉션 추가(쿼리 실행 안됨)
        return result.onEach { order ->
            order.orderItems = orderItemMap[order.orderId]!!
        }
    }

    private fun toOrderIds(result: List<OrderQueryDto>): List<Long> {
        return result.map { it.orderId }
    }

    private fun findOrderItemMap(orderIds: List<Long>): Map<Long, List<OrderItemQueryDto>> {
        val orderItems: List<OrderItemQueryDto> = em.createQuery(
            "select new jpabook.jpashop.repository.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                " from OrderItem oi" +
                " join oi.item i" +
                " where oi.order.id in :orderIds", OrderItemQueryDto::class.java
        ).setParameter("orderIds", orderIds).resultList

        return orderItems.stream().collect(Collectors.groupingBy(OrderItemQueryDto::orderId));
    }
}
