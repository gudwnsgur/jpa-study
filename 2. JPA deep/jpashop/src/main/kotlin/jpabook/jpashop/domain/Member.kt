package jpabook.jpashop.domain

import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */

@Entity
data class Member(
    @Id @GeneratedValue
    @Column(name = "member_id")
    val id: Long,

    // val : Getter
    // var : Getter + Setter
    val name: String,

    @Embedded
    val address: Address,

    @OneToMany(mappedBy = "member")
    val orders: MutableList<Order> = arrayListOf()
)
