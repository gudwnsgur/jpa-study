package com.study.querydsl.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * @author Joonhyuck Hyoung
 */
@Entity
data class Hello(
    @Id @GeneratedValue
    val id: Long = 0L,
)
