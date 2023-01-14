package com.study.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import com.study.querydsl.entity.Hello
import com.study.querydsl.entity.QHello
import io.jsonwebtoken.lang.Assert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class QuerydslApplicationTests @Autowired constructor(
	private val em: EntityManager
){

	@Test
	fun contextLoads() {
		val hello = Hello()
		em.persist(hello)

		val query = JPAQueryFactory(em)
		val qHello = QHello.hello

		val result = query.selectFrom(qHello).fetchOne()

		Assertions.assertEquals(result, hello)
		Assertions.assertEquals(result?.id, hello.id)
	}
}
