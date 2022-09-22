package jpabook.jpashop.domain.item

import jpabook.jpashop.domain.*
import jpabook.jpashop.exception.*
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
open class Item constructor(
    val name: String,

    val price: Int,

    var stockQuantity: Int,
) {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    val id: Long = 0L

    @ManyToMany(mappedBy = "items")
    val categories: MutableList<Category> = arrayListOf()

    //== 비즈니스 로직 ==//
    fun addStock(quantity: Int) {
        stockQuantity += quantity
    }

    fun removeStock(quantity: Int) {
        (stockQuantity - quantity).also { restStock ->
            if(restStock < 0) throw NotEnoughStockException("need more stock")
            stockQuantity = restStock
        }
    }
}
