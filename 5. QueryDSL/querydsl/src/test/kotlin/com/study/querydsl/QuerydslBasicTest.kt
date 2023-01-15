package com.study.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import com.study.querydsl.domain.Member
import com.study.querydsl.domain.QMember
import com.study.querydsl.domain.QMember.* // like this
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
) {
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

    @Test
    fun `기본 Q-Type 활용`() {
        val qMember1 = QMember("m") // 별칭을 직접 지정
        val qMember2 = member // 기본 인스턴스 사용

        /**
         * static import로 추가할수도 있다.
         */
        val member = queryFactory.select(member)
            .from(member)
            .where(member.username.eq("member1"))
            .fetchOne()

        Assertions.assertEquals("member1", member?.username)

        /**
         * 같은 테이블을 조인해야 하는 경우 테이블명이 달라져야하기 때문에 별칭을 지정해야 하지만
         * 그게 아니라면 기본 인스턴스를 쓰거나 import 해서 쓰는걸 권장한다.
         */
    }

    @Test
    fun `검색 조건 쿼리 - 기본 검색 쿼리`() {
        /**
         *  검색 조건은 .and(), .or() 메서드 체인을 연결 가능
         *  select(member).from(member) == selectFrom(member)
         */
        val member1 = queryFactory
            .selectFrom(member)
            .where(member.username.eq("member1")
                .and(member.age.eq(10)))
            .fetchOne()

        Assertions.assertEquals("member1", member1?.username)

        /**
         * JPQL이 제공하는 모든 검색 조건을 다 제공한다.
         */
        val member2 = queryFactory
            .selectFrom(member)
            .where(
                member.username.eq("member1"),       // username = 'member1'
                member.username.ne("member1"),       // username != 'member1'
                member.username.eq("member1").not(), // username != 'member1'
                member.username.isNotNull,                 // 이름이 is not null
                member.age.`in`(10, 20),            // age in (10,20)
                member.age.notIn(10, 20),           // age not in (10, 20)
                member.age.between(10, 30),                // between 10, 30
                member.age.goe(30),                  // age >= 30

                member.age.gt(30),                   // age > 30
                member.age.loe(30),                  // age <= 30
                member.age.lt(30),                   // age < 30
                member.username.like("member%"),      // like 검색
                member.username.contains("member"),       // like ‘%member%’ 검색
                member.username.startsWith("member"),     // like ‘member%’ 검색
            )
    }

    @Test
    fun `검색 조건 처리 - AND 조건을 파라미터로 처리`() {
        val result = queryFactory
            .selectFrom(member)
            .where(
                member.username.eq("member1"),
                member.age.eq(10),
                null, // 무시
            )
            .fetch()

        Assertions.assertEquals(1, result.size)
        /**
         * where()에 파라미터로 검색조건을 추가하면 AND 조건이 추가된다.
         * 조건의 결과가 null이면 무시 => 동적 쿼리를 깔끔하게 만들 수 있다.
         */
    }

    @Test
    fun `결과 조회`() {
        val membersByFetch = queryFactory.selectFrom(member).fetch() // list
        val memberByFetchOne = queryFactory.selectFrom(member).where(member.age.eq(10)).fetchOne() // 단건
        val memberByFetchFirst = queryFactory.selectFrom(member).fetchFirst() // 리스트중 첫 한건
        val membersWithPaging = queryFactory.selectFrom(member).fetchResults() // 페이징에서 사용
        val membersCount = queryFactory.selectFrom(member).fetchCount() // count 쿼리로 변경


        println("======================== .fetch() ========================")
        println(membersByFetch)
        println("======================== .fetchOne() =====================")
        // 단건이 아닌데 쓰면 NonUniqueResultException
        println(memberByFetchOne)
        println("======================== .fetchFirst() ===================")
        println(memberByFetchFirst)
        println("======================== .fetchResults() =================")
        println(membersWithPaging.results)
        println("======================== .fetchCount()  ==================")
        println(membersCount)
        println("==========================================================")
    }

    @Test
    fun `정렬`() {
        /**
         * 회원 정렬 순서
         * 1. 회원 나이 내림차순(desc)
         * 2. testForNullLast 컬럼 올림차순(asc)
         * 단 2에서 회원 이름이 없으면 마지막에 출력(nulls last)
         *
         * desc(), asc() 일반 정렬
         * nullsLast(), nullsFirst() : null 데이터 순서 부여
         */
        em.persist(Member(username ="member5", age = 35, testForNullLast = 1))
        em.persist(Member(username ="member6", age = 35, testForNullLast = null))
        em.persist(Member(username ="member7", age = 35, testForNullLast = 2))

        val result = queryFactory
            .selectFrom(member)
            .orderBy(member.age.desc(), member.testForNullLast.asc().nullsLast())
            .fetch()


        result.forEach { println(it) }
        /**
            Member(id=6 username=member4, age=40, testForNullLast=0)
            Member(id=7 username=member5, age=35, testForNullLast=1)
            Member(id=9 username=member7, age=35, testForNullLast=2)
            Member(id=8 username=member6, age=35, testForNullLast=null)
            Member(id=5 username=member3, age=30, testForNullLast=0)
            Member(id=4 username=member2, age=20, testForNullLast=0)
            Member(id=3 username=member1, age=10, testForNullLast=0)
         */
    }
}

