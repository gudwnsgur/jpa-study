package study.jpa.basic.proxy.code;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author Joonhyuck Hyoung
 */
public class ProxyMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("basic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member = Member.create("형준혁");
            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("==============AAAAAA================");
            Member newMember = em.find(Member.class, member.getId());

            System.out.println("==============BBBBBB=================");

            System.out.println("newMember.id = " + newMember.getId()); // newMember.id = 1
            System.out.println("newMember.username = " + newMember.getUserName()); // newMember.username = 형준혁

            System.out.println("============CCCCCCC=============");

            em.flush();
            em.clear();

            System.out.println("==============DDDDDDD=================");

            Member proxyMember = em.getReference(Member.class, member.getId());

            System.out.println("==============EEEEEEE===================");

            System.out.println("proxyMember.id = " + proxyMember.getId()); // newMember.id = 1
            System.out.println("==============FFFFFFF===================");
            System.out.println("proxyMember.username = " + proxyMember.getUserName()); // newMember.username = 형준혁
            System.out.println("=============GGGGGGGG==================");

            tx.commit();
            System.out.println("=============HHHHHHH==================");
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}

/**
 * RESULT
 * ==============AAAAAA================
 *  select
 *         member0_.id as id1_7_0_,
 *         member0_.TEAM_ID as team_id3_7_0_,
 *         member0_.userName as username2_7_0_,
 *         team1_.id as id1_8_1_,
 *         team1_.teamName as teamname2_8_1_
 *     from
 *         proxy_member member0_
 *     left outer join
 *         proxy_team team1_
 *             on member0_.TEAM_ID=team1_.id
 *     where
 *         member0_.id=?
 *
 * ==============BBBBBB=================
 *
 * newMember.id = 1
 * newMember.username = 형준혁
 *
 * ============CCCCCCC=============
 * ==============DDDDDDD=================
 * ==============EEEEEEE===================
 *
 * proxyMember.id = 1
 *
 * ==============FFFFFFF===================
 *
 * select
 *         member0_.id as id1_7_0_,
 *         member0_.TEAM_ID as team_id3_7_0_,
 *         member0_.userName as username2_7_0_,
 *         team1_.id as id1_8_1_,
 *         team1_.teamName as teamname2_8_1_
 *     from
 *         proxy_member member0_
 *     left outer join
 *         proxy_team team1_
 *             on member0_.TEAM_ID=team1_.id
 *     where
 *         member0_.id=?
 *
 * proxyMember.username = 형준혁
 *
 * =============GGGGGGGG==================
 * =============HHHHHHH==================
 */
