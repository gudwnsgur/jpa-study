package study.jpa.basic.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import study.jpa.basic.Member;

/**
 * @author Joonhyuck Hyoung
 */
public class CacheMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
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

        emf.close();
    }
}
