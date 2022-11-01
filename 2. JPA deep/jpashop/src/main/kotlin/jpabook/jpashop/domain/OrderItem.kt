package jpabook.jpashop.domain

import jpabook.jpashop.domain.item.*
import net.minidev.json.annotate.JsonIgnore
import javax.persistence.*

@Entity
data class OrderItem(
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    val id: Long = 0L,

    /** 주문 상품 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    val item: Item,

    /** 주문 */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order? = null,

    /** 주문 가격 */
    val orderPrice: Int,

    /** 주문 수량 */
    val count: Int,
) {
    //== 비즈니스 로직 ==//
    /** 주문 취소 **/
    fun cancel() {
        item.addStock(count)
    }

    //== 조회 로직 ==//
    /** 주문상품 전체 가격 조회 **/
    fun getTotalPrice() = orderPrice + count

    //== 생성 메서드 ==//
    companion object {
        fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem {
            return OrderItem(
                item = item,
                orderPrice = orderPrice,
                count = count,
            ).also {
                it.item.removeStock(count)
            }
        }
    }
}
