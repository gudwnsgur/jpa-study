package jpabook.jpashop.domain.item

import javax.persistence.*

/**
 * @author Joonhyuck Hyoung
 */
@Entity
@DiscriminatorValue("ALBUM")
class Album(
    val artist: String,
    val etc: String,

    name: String,
    price: Int,
    stockQuantity: Int,
) : Item(name, price, stockQuantity)
