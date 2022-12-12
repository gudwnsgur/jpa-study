package jpabook.springdatajpa.domain

import org.springframework.data.jpa.repository.*

/**
 * @author Joonhyuck Hyoung
 */
interface MemberRepository : JpaRepository<Member, Long>
