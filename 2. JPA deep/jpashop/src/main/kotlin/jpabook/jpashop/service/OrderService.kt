package jpabook.jpashop.service

import jpabook.jpashop.domain.*
import jpabook.jpashop.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Joonhyuck Hyoung
 */
@Service
@Transactional(readOnly = true)
class OrderService(
    private val orderRepository: OrderRepository,
    private val memberRepository: MemberRepository,
    private val itemRepository: ItemRepository,
) {
    /** 주문 **/
    @Transactional
    fun order(memberId: Long, itemId: Long, count: Int): Long {
        // 엔티티 조회
        val member = memberRepository.findOne(memberId)?: throw IllegalStateException("회원이 존재하지 않습니다.")
        val item = itemRepository.findOne(itemId)?: throw IllegalStateException("상품이 존재하지 않습니다.")

        // 배송정보 생성
        val delivery = Delivery.create(
            address = member.address,
            status = DeliveryStatus.READY
        )

        // 주문상품 생성
        val orderItem = OrderItem.createOrderItem(
            item = item,
            orderPrice = item.price,
            count = count
        )

        // 주문 생성
        val order = Order.createOrder(member, delivery, orderItem)

        // 주문 저장
        orderRepository.save(order)

        return order.id
    }

    /** 주문 취소 **/
    @Transactional
    fun cancelOrder(orderId: Long) {
        orderRepository.findOne(orderId)?.also {
            it.cancel()
        }
    }

    /** 주문 검색 **/
//    fun findOrders(orderSearch: OrderSearch) = orderRepository.findAll(orderSearch)
}
