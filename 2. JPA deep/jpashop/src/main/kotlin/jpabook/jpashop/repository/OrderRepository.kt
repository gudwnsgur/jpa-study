package jpabook.jpashop.repository

import jpabook.jpashop.domain.*
import org.springframework.stereotype.*
import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
@Repository
class OrderRepository(
    // 엔티티 메니저( EntityManager ) 주입
    @PersistenceContext
    private val em: EntityManager,
) {
    fun save(order: Order) {
        em.persist(order)
    }

    fun findOne(id: Long): Order? = em.find(Order::class.java, id)

    fun findAllByString(orderSearch: OrderSearch? = null): List<Order> {
        var jpql = "select o From Order o join o.member m"
        var isFirstCondition = true

        //주문 상태 검색
        if (orderSearch?.orderStatus != null) {
            jpql += " where o.status = :status"
            isFirstCondition = false
        }

        //회원 이름 검색
        if (!orderSearch?.memberName.isNullOrBlank()) {
            jpql +=
                if (isFirstCondition) " where"
                else " and"
            jpql += " m.name like :name"
        }

        var query = em.createQuery(jpql, Order::class.java)
            .setMaxResults(1000) //최대 1000건

        if (orderSearch?.orderStatus != null) {
            query = query.setParameter("status", orderSearch.orderStatus)
        }
        if (!orderSearch?.memberName.isNullOrBlank()) {
            query = query.setParameter("name", orderSearch?.memberName)
        }
        return query.resultList
    }

    /**
     * V3: 엔티티를 DTO로 변환 - 페치 조인 최적화
     * - order를 조회할때 member, delivery, orderItems, item 전부 가져온다.
     * - fetch는 JPA에만 있는 문법
     * - 기가 막히게 쿼리가 단 한번 나간다!
     * - 쿼리 한번에 결과를 다 가져와버려서 지연로딩 자체가 일어나지 않는다.
     * - 실무에서 fetch join을 정말 자주 사용하기 때문에 100% 이해해야 한다.
     * - 실무에서 JPA 성능문제의 90%는 N+1 문제인데 이거 하나 잘 알아놓으면 된다?
     */
    fun findAllWithItem(): List<Order> {
        return em.createQuery(
            "select distinct o from Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d" +
                " join fetch o.orderItems oi" +
                " join fetch oi.item i", Order::class.java
        ).resultList
    }

    fun findAllWithMemberDelivery(offset: Int, limit: Int): List<Order> {
        return em.createQuery(
            "select o from Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d", Order::class.java
        ).apply {
            firstResult = offset
            maxResults = limit
        }.resultList
    }
}
