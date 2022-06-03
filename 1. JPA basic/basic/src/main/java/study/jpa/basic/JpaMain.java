package study.jpa.basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author Joonhyuck Hyoung
 *
 * JPA의 정석
 */
public class JpaMain {
    public static void main(String[] args) {
        // EntityManagerFactory
        // 애플리케이션 로딩시점에 딱 하나만 만들어져야 한다.
        // 애플리케이션 전체에서 공유되어야 하기 때문에
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");


        // EntityManager
        // 자바 Collection처럼 객체를 대신 저장해주는 놈이라고 생각하면 편하다.
        // thread간 공유하지 않으므로 사용하고 버려야 한다.
        EntityManager em = emf.createEntityManager();

        // EntityTransaction
        // JPA의 모든 데이터 변경은 transaction 안에서 실행해야 한다.
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            // INSERT
            Member member = new Member();
            member.setId(1L);
            member.setName("형준혁");
            em.persist(member);

            // SELECT
            Member member2 = em.find(Member.class, 1L);

            // DELETE
            em.remove(member2);

            /**
             * UPDATE
             * JPA를 통해 entity를 가져오면 jpa가 관리한다.
             * JPA가 변경사항을 transaction이 commit 되는시점에 확인하여 update 진행
             */
            member2.setName("형준혁2");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
