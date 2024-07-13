package jpql;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            for (int i = 1; i <= 10; i++) {
                Member member = new Member();
                Team team = new Team();
                team.setName("team" + i);
                em.persist(team);

                member.setUsername("member" + i);
                member.setAge(i+16);
                member.setTeam(team);
                em.persist(member);
            }

            em.flush();
            em.clear();

            System.out.println("======== START ========");
            String query = "select m from Member m where m.age > (select avg(m2.age) from Member m2)";

            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

            System.out.println("result size: " + result.size());

            for (Member member : result) {
                System.out.println("member = " + member + ", team name: " + member.getTeam().getName());
            }
            System.out.println("======== END ========");
            
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}