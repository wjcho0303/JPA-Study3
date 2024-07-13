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
                member.setType(MemberType.USER);
                em.persist(member);
            }

            em.flush();
            em.clear();

            System.out.println("======== 1 START ========");
            String query = "select m from Member m where m.type = jpql.MemberType.USER";

            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

            System.out.println("result size: " + result.size());

            for (Member member : result) {
                System.out.println("member = " + member + ", team name: " + member.getTeam().getName());
            }
            System.out.println("======== 1 END ========");



            // 패키지명을 파라미터 바인딩으로 해결
            System.out.println("======== 2 START ========");
            String query2 = "select m from Member m where m.type = :userType";

            List<Member> result2 = em.createQuery(query2, Member.class)
                    .setParameter("userType", MemberType.USER)
                    .getResultList();

            System.out.println("result size: " + result.size());

            for (Member member : result2) {
                System.out.println("member = " + member + ", team name: " + member.getTeam().getName());
            }
            System.out.println("======== 2 END ========");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}