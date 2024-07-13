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

            Member member1 = new Member();
            member1.setUsername("홍길동");
            member1.setAge(15);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("김김김");
            member2.setAge(22);
            em.persist(member2);

            // TypedQuery : 반환 타입이 명확할 때 제네릭과 함께 사용하고, createQuery 에도 반환 타입을 명시한다.
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            List<Member> members = query1.getResultList();
            for (Member member : members) {
                System.out.println("memberId: " + member.getId() +
                        ", memberName: " + member.getUsername() +
                        ", memberAge: " + member.getAge());
            }

            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);

            TypedQuery<Integer> query3 = em.createQuery("select m.age from Member m", Integer.class);

            // Query : 반환 타입이 명확하지 않을 때
            Query query4 = em.createQuery("select m.username, m.age from Member m");



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}