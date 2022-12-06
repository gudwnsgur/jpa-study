package jpabook.jpashop.repository

import net.minidev.json.annotate.*

/**
 * @author Joonhyuck Hyoung
 */
data class OrderItemQueryDto(
    @JsonIgnore
    val orderId: Long,
    val itemName: String,
    val orderPrice: Int,
    val count: Int,
) {
    companion object {
        fun valueOf(orderId: Long, itemName: String, orderPrice: Int, count: Int) = OrderItemQueryDto(
            orderId = orderId,
            itemName = itemName,
            orderPrice = orderPrice,
            count = count
        )
    }
}
