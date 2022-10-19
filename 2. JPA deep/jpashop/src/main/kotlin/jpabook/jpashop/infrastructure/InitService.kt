package jpabook.jpashop.infrastructure

import jpabook.jpashop.domain.*
import jpabook.jpashop.domain.item.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*
import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
@Component
@Transactional
class InitService(
    // 엔티티 메니저( EntityManager ) 주입
    @PersistenceContext
    private val em: EntityManager,
) {
    fun dbInit1() {
        val member = Member(
            name = "형준혁",
            address = Address(
                city = "서울",
                street = "구로",
                zipcode = "15341"
            )
        )
        em.persist(member)

        val book1 = Book(
            name = "JPA1 BOOK",
            price = 10000,
            stockQuantity = 100
        )
        em.persist(book1)
        val book2 = Book(
            name = "JPA2 BOOK",
            price = 20000,
            stockQuantity = 200
        )
        em.persist(book2)

        val orderItem1 = OrderItem(
            item = book1,
            orderPrice = 10000,
            count = 1
        )
        val orderItem2 = OrderItem(
            item = book2,
            orderPrice = 20000,
            count = 2
        )
        val order = Order(
            member = member,
            orderItems = mutableListOf(orderItem1, orderItem2),
            delivery = Delivery(address = member.address),
        )
        em.persist(order)
    }

    fun dbInit2() {
        val member = Member(
            name = "이찬우",
            address = Address(
                city = "분당",
                street = "미금",
                zipcode = "11232"
            )
        )
        em.persist(member)

        val book1 = Book(
            name = "clean code1",
            price = 20000,
            stockQuantity = 200
        )
        em.persist(book1)
        val book2 = Book(
            name = "clean code2",
            price = 40000,
            stockQuantity = 300
        )
        em.persist(book2)

        val orderItem1 = OrderItem(
            item = book1,
            orderPrice = 20000,
            count = 3
        )
        val orderItem2 = OrderItem(
            item = book2,
            orderPrice = 40000,
            count = 3
        )
        val order = Order(
            member = member,
            orderItems = mutableListOf(orderItem1, orderItem2),
            delivery = Delivery(address = member.address),
        )
        em.persist(order)
    }
}
