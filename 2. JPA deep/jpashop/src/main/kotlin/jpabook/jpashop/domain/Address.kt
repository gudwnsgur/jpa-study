package jpabook.jpashop.domain

import javax.persistence.Embeddable

@Embeddable
data class Address(
    val city: String,
    val street: String,
    val zipcode: String,
)
