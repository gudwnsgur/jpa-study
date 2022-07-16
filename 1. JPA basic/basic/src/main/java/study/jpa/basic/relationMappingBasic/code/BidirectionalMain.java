package study.jpa.basic.relationMappingBasic.code;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author Joonhyuck Hyoung
 */
public class BidirectionalMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team team = Team.create("Chelsea");
            em.persist(team);

            Member member = Member.create(team, "N'Golo Kanté");
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getNo());
            List<Member> members = findMember.getTeam().getMembers();
            for(Member m: members) {
                System.out.println(m.toString());
                // Member{no=1, userName='N'Golo Kanté', team=Team{teamNo=1, teamName='Chelsea'}}
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
