package study.jpa.basic.entityMapping.code;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author Joonhyuck Hyoung
 */
public class PkMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member = Member.create(
                "형준혁", 26, RoleType.ADMIN, LocalDate.now(), LocalDateTime.now(), "this is description"
            );
            System.out.println("========================");
            em.persist(member);
            System.out.println("========================");
            tx.commit();
            System.out.println("========================");
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        /**
         * Type : Identity는 commit 전에 persist하면 insert 쿼리가 날라간다 (커밋 안해도)
         * persist 시점에 디비에 집어넣고 그 값을 다시 읽어온다.
         * 그래서 모아서 인서트하는게 identity 전략에서는 불가능하다. (단점?)
         * 영한피셜 : 근데 사실 아 저도 참 이것저것 개발 많이 해봤는데 이게 크게 막 버퍼링해서 쓰는게 크게 막 메리트가 있지는 않아요.
         *          한 트랜잭션 안에서만 인서트 쿼리가 여러번 네트워크를 탄다고 해서 비약적으로 성능에 차이가 나지는 않더라~
         * ========================
         * insert study.jpa.basic.entityMapping.code.Member
         * insert into entity_mapping_member
         * (age, createdAt, description, modifiedAt, roleType, name)
         * values ( ?, ?, ?, ?, ?, ?)
         * ========================
         * ========================
         */
    }
}
