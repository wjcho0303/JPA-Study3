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
                member.setAge(6*i+16);
                member.setTeam(team);
                member.setType(MemberType.USER);
                em.persist(member);
            }

            em.flush();
            em.clear();

            System.out.println("======== 1 START ========");
            String query1 =
                    "SELECT " +
                            "CASE " +
                            "WHEN m.age <= 19 THEN '학생요금' " +
                            "WHEN m.age >= 60 THEN '경로요금' " +
                            "ELSE '일반요금' " +
                            "END " +
                            "FROM Member m";

            List<String> result = em.createQuery(query1, String.class)
                    .getResultList();

            System.out.println("result size: " + result.size());

            for (String s : result) {
                System.out.println("s = " + s);
            }
            System.out.println("======== 1 END ========");



            // COALESCE
            System.out.println("======== 2 START ========");

            Member memberNoName1 = new Member();
            em.persist(memberNoName1);

            Member memberNoName2 = new Member();
            em.persist(memberNoName2);

            Member memberHasName = new Member();
            memberHasName.setUsername("홍길동");
            em.persist(memberHasName);

            String query2 = "SELECT COALESCE (m.username, '이름 없는 회원') FROM Member m";
            List<String> result2 = em.createQuery(query2, String.class)
                    .getResultList();

            System.out.println("result2 size: " + result2.size());

            for (String s : result2) {
                System.out.println("s = " + s);
            }
            System.out.println("======== 2 END ========");


            // NULLIF
            System.out.println("======== 3 START ========");

            String query3 = "SELECT NULLIF (m.username, 'member5') FROM Member m";
            List<String> result3 = em.createQuery(query3, String.class)
                    .getResultList();

            System.out.println("result3 size: " + result3.size());

            for (String s : result3) {
                System.out.println("s = " + s);
            }
            System.out.println("======== 3 END ========");


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}