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
public class PersistenceMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            // 비영속
            Member member = new Member();
            member.setId(1L);
            member.setName("형준혁");

            // 영속
            // 이 때 DB에 저장되는것은 아니다.
            // EntityManager의 영속성 컨텍스트에 들어가는 것
            System.out.println("====== BEFORE ======");
            em.persist(member);
            System.out.println("====== AFTER ======");

            /** RESULT
            ====== BEFORE ======
            ====== AFTER ======
             insert study.jpa.basic.Member
             insert into Member
                (name, id)
             values
                (?, ?)
             */
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
