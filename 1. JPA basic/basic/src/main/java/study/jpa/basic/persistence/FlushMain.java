package study.jpa.basic.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import study.jpa.basic.Member;

/**
 * @author Joonhyuck Hyoung
 */
public class FlushMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member = new Member(1234L, "member1");
            em.persist(member);
            
            System.out.println("====BEFORE FLUSH====");
            em.flush();
            System.out.println("===after flush && before commit===");
            tx.commit();
            System.out.println("====AFTER COMMIT====");

            /*
              ====BEFORE FLUSH====

              insert study.jpa.basic.Member
              insert into
              Member (name, id) values (?, ?)

              ===after flush && before commit===

              ====AFTER COMMIT====
             */
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
