package jpabook.jpashop.service

import jpabook.jpashop.domain.*
import jpabook.jpashop.domain.item.*
import jpabook.jpashop.exception.*
import jpabook.jpashop.repository.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.*
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

/**
 * @author Joonhyuck Hyoung
 */
@SpringBootTest
@Transactional
internal class OrderServiceTest(
    @PersistenceContext
    private val em: EntityManager,
    private val orderService: OrderService,
    private val orderRepository: OrderRepository,
) {
    @Test
    fun `상품주문`() {
        // given
        val member = createMockMember()
        val item = createBook(
            name = "시골 JPA",
            price = 10000,
            stockQuantity = 10
        )
        val orderCount = 2

        // when
        val orderId = orderService.order(member.id, item.id, orderCount)

        // then
        val order = orderRepository.findOne(orderId)!!

        assertEquals(OrderStatus.ORDER, order.status)
        assertEquals(1, order.orderItems.size)
        assertEquals( 10000 * 2, order.getTotalPrice())
        assertEquals(8, item.stockQuantity)
    }

    @Test
    fun `상품주문_재고수량초과`() {
        // given
        val member = createMockMember()
        val item = createBook(
            name = "시골 JPA",
            price = 10000,
            stockQuantity = 10
        )
        val orderCount = 11 // 재고보다 많은 수량

        // when, then
        assertThrows<NotEnoughStockException> {
            orderService.order(member.id, item.id, orderCount)
        }
    }

    @Test
    fun `주문취소`() {
        // given
        val member = createMockMember()
        val item = createBook(
            name = "시골 JPA",
            price = 10000,
            stockQuantity = 10
        )
        val orderCount = 2

        val orderId = orderService.order(member.id, item.id, orderCount)

        // when
        orderService.cancelOrder(orderId)

        // then
        val order = orderRepository.findOne(orderId)!!
        assertEquals(OrderStatus.CANCEL, order.status)
        assertEquals(10, item.stockQuantity)
    }


    private fun createMockMember(): Member {
        val member = Member(
            name = "형준혁",
            address = Address(
                city = "서울",
                street = "구로",
                zipcode = "1232412"
            )
        )
        em.persist(member)
        return member
    }

    private fun createBook(name: String, price: Int, stockQuantity: Int): Book {
        val book = Book(
            name = name,
            price = price,
            stockQuantity = stockQuantity
        )
        em.persist(book)
        return book
    }
}
