package study.jpa.basic.jpashop;

import org.aspectj.weaver.ast.Or;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import study.jpa.basic.jpashop.domain.Member;
import study.jpa.basic.jpashop.domain.Order;
import study.jpa.basic.jpashop.domain.OrderItem;

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
            Order order = new Order(); // 안에 내용 있는게 맞음 그냥 귀찮아서 안넣었음
            order.addOrderItem(new OrderItem()); // 연관관계 편의 메서드

            // 양방향 연관관계가 아니어도 application 개발에 문제 없음

            // if not bidirectional
            Order myOrder = new Order();
            em.persist(myOrder);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            em.persist(orderItem);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
