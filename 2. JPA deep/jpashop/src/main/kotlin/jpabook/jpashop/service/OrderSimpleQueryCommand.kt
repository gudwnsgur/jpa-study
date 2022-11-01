package jpabook.jpashop.service

import jpabook.jpashop.domain.*
import java.time.*

/**
 * @author Joonhyuck Hyoung
 */
data class OrderSimpleQueryCommand(
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address,
) {
    companion object {
        fun of(
            orderId: Long, name: String, orderDate: LocalDateTime,
            orderStatus: OrderStatus, address: Address,
        ) = OrderSimpleQueryCommand(
            orderId = orderId,
            name = name,
            orderDate = orderDate,
            orderStatus = orderStatus,
            address = address
        )
    }
}
