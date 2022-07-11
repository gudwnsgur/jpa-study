package study.jpa.basic.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import study.jpa.basic.jpashop.domain.Member;
import study.jpa.basic.jpashop.domain.Order;

/**
 * @author Joonhyuck Hyoung
 */
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Order order = em.find(Order.class, 1L);
            Long memberNo = order.getMemberNo();
            Member member = em.find(Member.class, memberNo);
            /**
             * 주문에서 member_no를 받아와서 그걸로 또 Member를 뒤져봐야 한다.
             * 객체를 그냥 관계형 디비에 맞춰서 설계한것뿐임.. 객체지향적이지 못하다....
             *
             * Order 객체 내에 Member 객체가 있다면?
             */
            // Order order = em.find(Order.class, 1L);
            // Member member = order.getMember();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
