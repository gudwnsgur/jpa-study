package study.jpa.basic.advancedMapping.code;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author Joonhyuck Hyoung
 */
public class SuperclassMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member = new Member();
            member.setName("형준혁");
            member.setCreatedBy("형준혁");
            member.setCreatedDate(LocalDateTime.now());

            em.persist(member);

            /**
             * insert
             *         into
             *             Member
             *             (createdBy, createdDate, lastModifiedBy, lastModifiedDate, name, id)
             *         values
             *             (?, ?, ?, ?, ?, ?)
             */

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
