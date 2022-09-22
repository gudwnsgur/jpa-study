package jpabook.jpashop.domain

import javax.persistence.*

@Entity
data class Delivery(
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    val id: Long = 0L,

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    var order: Order? = null,

    @Embedded
    val address: Address? = null,

    @Enumerated(EnumType.STRING)
    val status: DeliveryStatus,
) {
    companion object {
        fun create(address: Address?, status: DeliveryStatus): Delivery {
            return Delivery(
                address = address,
                status = status
            )
        }
    }
}
