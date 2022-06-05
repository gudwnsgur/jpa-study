package study.jpa.basic.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import study.jpa.basic.Member;

/**
 * @author Joonhyuck Hyoung
 */
public class DirtyCheckingMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

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
