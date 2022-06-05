package study.jpa.basic.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import study.jpa.basic.Member;

/**
 * @author Joonhyuck Hyoung
 */
public class UpdateMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member = em.find(Member.class, 150L);
            // 이 시점에 entity 값이 수정된다.
            member.setName("ZZZZZ");


            // 1. flush()
            // 2. 엔티티와 스냅샷 비교
            // 3. 다르면 update SQL를 생성해서 쓰기지연 저장소에 저장
            // 4. 이후 DB에 한번에 반영
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
