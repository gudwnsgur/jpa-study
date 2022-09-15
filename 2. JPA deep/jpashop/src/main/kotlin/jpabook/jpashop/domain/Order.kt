package jpabook.jpashop.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue
    @Column(name = "order_id")
    val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private var member: Member,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderItem: List<OrderItem> = emptyList(),

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    val delivery: Delivery,

    val orderDate: LocalDateTime, // 주문 시간

    @Enumerated(EnumType.STRING)
    val status: OrderStatus, // 주문 상세 (ORDER, CANCEL)
) {

    // == 연관관계 메서드 == //
    fun setMember(member: Member) {
        this.member = member;
        member.orders.add(this)
    }

}
