package jpabook.jpashop.domain

import java.time.*
import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue
    @Column(name = "order_id")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private var member: Member,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderItems: MutableList<OrderItem> = arrayListOf(),

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private var delivery: Delivery,

    val orderDate: LocalDateTime = LocalDateTime.now(), // 주문 시간

    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.ORDER, // 주문 상세 (ORDER, CANCEL)
) {

    // == 연관관계 메서드 == //
    fun setMember(member: Member) {
        this.member = member;
        member.orders.add(this)
    }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }

    fun setDelivery(delivery: Delivery) {
        this.delivery = delivery
        delivery.order = this
    }

    //== 비즈니스 로직 ==//
    /** 주문 취소 **/
    fun cancel() {
        if (delivery.status == DeliveryStatus.COMP)
            throw IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.")

        status = OrderStatus.CANCEL
        orderItems.forEach { it.cancel() }
    }

    //== 조회 로직 ==//
    /** 전체 주문 가격 조회 **/
    fun getTotalPrice() = orderItems.sumOf {
        it.getTotalPrice()
    }

    //== 생성 메서드 ==//
    companion object {
        fun createOrder(member: Member, delivery: Delivery, orderItem: OrderItem): Order {
            return Order(
                member = member,
                delivery = delivery,
                status = OrderStatus.ORDER,
                orderDate = LocalDateTime.now(),
            ).also { order ->
                order.orderItems.add(orderItem)
            }
        }
    }
}
