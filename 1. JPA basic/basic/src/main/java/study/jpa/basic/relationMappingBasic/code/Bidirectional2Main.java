package study.jpa.basic.relationMappingBasic.code;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author Joonhyuck Hyoung
 */
public class Bidirectional2Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team team = Team.create("Chelsea");
            em.persist(team);

            Member member = Member.create(team, "kante");
            em.persist(member);

            // 1차 캐시를 지우지 않았을 때
//            em.flush();
//            em.clear();

            Team t = em.find(Team.class, team.getTeamNo());
            List<Member> members = t.getMembers(); // members에 값이 들어가있지 않다!!!
            for(Member m: members) {
                System.out.println(m.toString());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
