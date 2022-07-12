package study.jpa.basic.relationMappingBasic.code;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
            // 팀을 등록
            Team team = Team.create("팀이름");
            em.persist(team);

            // 멤버를 등록
            Member member = Member.create(team, "형준혁");
            em.persist(member);

            // DB에서 직접 조회하는걸 보고싶으면 (1차 캐시x)
            em.flush();
            em.clear();

            // member & team 조회
            Member newMember = em.find(Member.class, member.getNo());
            /**
             *  select member0_.no as no1_6_0_,
             *        member0_.teamNo as teamno3_6_0_,
             *        member0_.userName as username2_6_0_,
             *        team1_.teamNo as teamno1_7_1_,
             *        team1_.teamName as teamname2_7_1_
             *  from
             *        relation_mapping_basic_member member0_
             *  left outer join
             *         relation_mapping_basic_team team1_
             *             on member0_.teamNo=team1_.teamNo
             *  where
             *         member0_.no=?
             */
            Team newTeam = newMember.getTeam();
            System.out.println("team name : " + newTeam.getTeamName()); // team name : 팀이름

            em.flush();
            em.clear();

            // 수정
            for(int i=0; i<=100; i++) em.persist(Team.create("팀 이름" + i));
            Member member1 = em.find(Member.class, 1L);

            /**
             * update
             * study.jpa.basic.relationMappingBasic.code.Member
             *      relation_mapping_basic_member
             * set
             *      teamNo=?, userName=?
             * where
             *      no=?
             */
            member1.setTeam(em.find(Team.class, 100L));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
