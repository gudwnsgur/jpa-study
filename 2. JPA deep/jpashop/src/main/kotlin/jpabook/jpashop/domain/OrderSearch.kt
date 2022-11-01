package jpabook.jpashop.domain

/**
 * @author Joonhyuck Hyoung
 */
data class OrderSearch(
    // 회원 이름
    val memberName: String? = null,
    // 주문 상태 [ORDER, CANCEL]
    val orderStatus: OrderStatus? = null,
)
