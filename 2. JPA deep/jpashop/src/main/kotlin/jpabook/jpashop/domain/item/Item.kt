package jpabook.jpashop.domain.item

import jpabook.jpashop.domain.*
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
open class Item constructor(
    val name: String,

    val price: Int,

    val stockQuantity: Int,
) {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    val id: Long? = null

    @ManyToMany(mappedBy = "items")
    val categories: MutableList<Category> = arrayListOf()
}
