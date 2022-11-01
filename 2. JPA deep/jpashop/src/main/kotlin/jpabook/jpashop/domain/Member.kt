package jpabook.jpashop.domain

import net.minidev.json.annotate.JsonIgnore
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

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    val orders: MutableList<Order> = arrayListOf()
)
