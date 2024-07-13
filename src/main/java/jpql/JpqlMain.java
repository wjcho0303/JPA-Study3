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

            em.flush();
            em.clear();

            List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            Member findMember = members.get(0);
            findMember.setAge(20);

            for (Member member : members) {
                System.out.println("member name: " + member.getUsername() + ", member age: " + member.getAge());
            }

            List<Team> team = em.createQuery("select m.team from Member m", Team.class)
                    .getResultList();

            List<Address> addressList = em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();
            for (Address address : addressList) {
                System.out.println("address = " + address);
            }

            em.createQuery("select m.username, m.age from Member m")
                            .getResultList();

            List<MemberDTO> result = em.createQuery(
                    "select new jpql.MemberDTO(m.username, m.age) from Member m",
                            MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO username = " + memberDTO.getUsername());
            System.out.println("memberDTO age = " + memberDTO.getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}