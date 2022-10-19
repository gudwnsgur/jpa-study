package jpabook.jpashop.domain

import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */

@Entity
data class Member(
    @Id @GeneratedValue
    @Column(name = "member_id")
    val id: Long = 0L,

    // val : Getter
    // var : Getter + Setter
    var name: String,

    @Embedded
    val address: Address? = null,

    @OneToMany(mappedBy = "member")
    val orders: MutableList<Order> = arrayListOf()
)
