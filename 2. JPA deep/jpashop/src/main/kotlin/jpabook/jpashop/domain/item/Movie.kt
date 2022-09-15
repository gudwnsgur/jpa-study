package jpabook.jpashop.domain.item

import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
@Entity
@DiscriminatorValue("MOVIE")
class Movie(
    val director: String,
    val actor: String,

    name: String,
    price: Int,
    stockQuantity: Int,
) : Item(name, price, stockQuantity)
