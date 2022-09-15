package jpabook.jpashop.domain

import javax.persistence.*

@Entity
data class Delivery(
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    val id: Long,

    @OneToOne(mappedBy = "delivery")
    val order: Order,

    @Embedded
    val address: Address,

    val status: OrderStatus,
)
