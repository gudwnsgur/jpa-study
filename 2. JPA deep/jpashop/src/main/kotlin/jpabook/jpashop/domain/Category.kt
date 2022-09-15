package jpabook.jpashop.domain

import jpabook.jpashop.domain.item.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
class Category(
    @Id @GeneratedValue
    val id: Long,

    val name: String,

    @ManyToMany
    val items: List<Item> = emptyList(),

//    val parent: Category,

//    val child: List<Category> = emptyList(),
)
