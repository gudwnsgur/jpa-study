package com.study.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import com.study.querydsl.domain.Member
import com.study.querydsl.domain.QMember
import com.study.querydsl.domain.Team
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

/**
 * @author Joonhyuck Hyoung
 */
@SpringBootTest
@Transactional
class QuerydslBasicTest @Autowired constructor(
    private val em: EntityManager,
){
    private lateinit var queryFactory: JPAQueryFactory

    @BeforeEach
    fun before() {
        val teamA = Team(name = "teamA")
        val teamB = Team(name = "teamB")
        em.persist(teamA)
        em.persist(teamB)

        val member1 = Member(username = "member1", age = 10, team = teamA)
        val member2 = Member(username = "member2", age = 20, team = teamA)
        val member3 = Member(username = "member3", age = 30, team = teamB)
        val member4 = Member(username = "member4", age = 40, team = teamB)

        em.persist(member1)
        em.persist(member2)
        em.persist(member3)
        em.persist(member4)

        // EntityManager로 JPAQueryFactory 생성
        queryFactory = JPAQueryFactory(em)
    }

    @Test
    fun startJPQL() {
        val qlString = "select m from Member m " +
            "where m.username = :username"

        /**
         * JPQL
         * 런타임 시점 오류
         * 파라미터 바인딩을 직접
         */
        val member = em.createQuery(qlString, Member::class.java)
            .setParameter("username", "member1")
            .singleResult

        Assertions.assertEquals("member1", member.username)
    }

    @Test
    fun startQuerydsl() {
        val m = QMember("m")

        /**
         * QueryDsl
         * 컴파일 시점 오류
         * 파라미터 바인딩 자동 처리
         */
        val member = queryFactory.select(m)
            .from(m)
            .where(m.username.eq("member1"))
            .fetchOne()

        Assertions.assertEquals("member1", member?.username)
    }
}

