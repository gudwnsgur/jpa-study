package jpabook.jpashop.domain.item

import jpabook.jpashop.domain.*
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

/**
 * @author Joonhyuck Hyoung
 */
@Entity
@DiscriminatorValue("BOOK")
class Book(
    val author: String,
    val isbn: String,

    name: String,
    price: Int,
    stockQuantity: Int,
) : Item(name, price, stockQuantity)
