package jpabook.jpashop

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

//@Entity
data class Member(
    @Id @GeneratedValue
    val id: Long = 0L,
    val userName: String?,
)
/**
 * create table member (
 *    id bigint not null,
 *    user_name varchar(255),
 *    primary key (id)
 *  )
 */
