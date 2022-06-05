package study.jpa.basic.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import study.jpa.basic.Member;

/**
 * @author Joonhyuck Hyoung
 * <p>
 * JPA의 정석
 */
public class WriteMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        /** 1차 캐시 */
        try {
            Member member = new Member();
            member.setId(10L);
            member.setName("형준혁");

            // 이때 10L 의 member 캐시에 저장
            em.persist(member);

            // SELECT 쿼리가 안날라간다.
            // 이미 캐시에 member(10L)가 있기 때문에 DB 조회 x
            Member findMember = em.find(Member.class, 10L);


            // 여기서 조회할때 캐시 확인, 없으면 DB 조회 + 캐시 저장 + 반환
            Member m1 = em.find(Member.class, 1L);
            // 위에서 캐시에 member(1L)을 저장해놨으므로 Select문 x
            Member m2 = em.find(Member.class, 1L);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        tx.begin();
        /** 동일성 보장 */
        try {
            Member member1 = em.find(Member.class, "member1");
            Member member2 = em.find(Member.class, "member1");

            System.out.println(member1 == member2); // true

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        tx.begin();
        /** 쓰기 지연 */
        try {
            Member member1 = new Member(1L, "A");
            Member member2 = new Member(2L, "B");

            em.persist(member1);
            em.persist(member2);

            System.out.println("====================");
            // 이 때 member1, member2를 DB에 저장
            tx.commit();

            // Result
            // ====================
            // INSERT (1L, "A")
            // INSERT (2L, "B")
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
