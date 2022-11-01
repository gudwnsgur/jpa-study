package jpabook.jpashop.domain

import net.minidev.json.annotate.JsonIgnore
import javax.persistence.*

@Entity
data class Delivery(
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    val id: Long = 0L,

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    var order: Order? = null,

    @Embedded
    val address: Address? = null,

    @Enumerated(EnumType.STRING)
    val status: DeliveryStatus = DeliveryStatus.READY,
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
