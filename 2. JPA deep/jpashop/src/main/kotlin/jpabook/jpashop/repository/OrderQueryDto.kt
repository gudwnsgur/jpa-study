package jpabook.jpashop.repository

import jpabook.jpashop.domain.*
import java.time.*

/**
 * @author Joonhyuck Hyoung
 */
data class OrderQueryDto(
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime, //주문시간
    val orderStatus: OrderStatus,
    val address: Address,
    var orderItems: List<OrderItemQueryDto> = emptyList(),
) {
    companion object {
        fun valueOf(orderId: Long, name: String, orderDate: LocalDateTime, orderStatus: OrderStatus, address: Address) = OrderQueryDto(
            orderId = orderId,
            name = name,
            orderDate = orderDate,
            orderStatus = orderStatus,
            address = address,
        )
    }
}
