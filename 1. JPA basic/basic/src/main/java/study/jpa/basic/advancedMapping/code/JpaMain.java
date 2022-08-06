package study.jpa.basic.advancedMapping.code;

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
            Movie movie = new Movie();
            movie.setDirector("형준혁");
            movie.setActor("형준혁");
            movie.setName("바람과 함께 사라지다");
            movie.setPrice(1000000);

            em.persist(movie);

            /*
            insert into Item
                (name, price, id)
            values
                (?, ?, ?)

            insert into Movie
                (actor, director, id)
            values
                (?, ?, ?)
             */

            em.flush(); // 영속성 컨텍스트에 있는걸 디비에 다 날리고
            em.clear(); // 영속성 컨텍스트 깔끔하게 제거

            Movie findMovie = em.find(Movie.class, movie.getId());
            /*
             * Hibernate:
             *     select
             *         movie0_.id as id1_3_0_,
             *         movie0_1_.name as name2_3_0_,
             *         movie0_1_.price as price3_3_0_,
             *         movie0_.actor as actor1_4_0_,
             *         movie0_.director as director2_4_0_
             *     from
             *         Movie movie0_
             *     inner join
             *         Item movie0_1_
             *             on movie0_.id=movie0_1_.id
             *     where
             *         movie0_.id=?
             */

            System.out.println("findMovie = " + findMovie.toString());
            // findMovie = Movie{director='형준혁', actor='형준혁'}
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
