package jpabook.jpashop.domain

import javax.persistence.*

@Entity
data class Delivery(
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    val id: Long,

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    var order: Order,

    @Embedded
    val address: Address,

    @Enumerated(EnumType.STRING)
    val status: DeliveryStatus,
)
