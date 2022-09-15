package jpabook.jpashop.domain

import jpabook.jpashop.domain.item.*
import javax.persistence.*

@Entity
data class OrderItem(
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    val id: Long,

    /** 주문 상품 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    val item: Item,

    /** 주문 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    val order: Order,

    /** 주문 가격 */
    val orderPrice: Int,

    /** 주문 수량 */
    val count: Int,
)
