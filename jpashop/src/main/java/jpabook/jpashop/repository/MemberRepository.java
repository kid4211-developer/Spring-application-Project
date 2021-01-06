package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository //Component Scan�� ���� �ڵ����� Spring Bean���� ���/������
@RequiredArgsConstructor
public class MemberRepository {
	
	private final EntityManager em; //JPA ���� ǥ�� API - Spring�� �ش� EntityManager�� ���� Injection ����
	
	/* JPA�� Member ��ü�� �����ϴ� �޼��� */
	public void save(Member member) {
		/* persistence(���Ӽ�) Context�� Member��ü�� �ø���, key : PrimaryKey(id)�� ��������
		 * -> �׷��� MemberService�� join�޼��忡�� member.getId()�� ���� ���� �����°� ����� 
		 */
		em.persist(member); 
	}
	
	/* �־��� id�� �ش��ϴ� �ϳ��� Member ��ü�� Search�ϴ� �޼��� */
	public Member findOne(Long id) {
		Member member = em.find(Member.class, id);
		return member;
	}
	
	/* DB�� ����Ǿ� �ִ� Member ��ü ���θ� Search�ϴ� �޼��� */
	public List<Member> findAll(){
		List<Member> result = em.createQuery("select m from Memmber m", Member.class).getResultList(); //JPQL ����?
		return result;
	}
	
	/* Name���� Member�� Search�ϴ� �޼��� */
	public List<Member> findByName(String name){
		List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name",name).getResultList();
		return result;
	}
}











