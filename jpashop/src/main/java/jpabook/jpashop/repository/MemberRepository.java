package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository //Component Scan에 의해 자동으로 Spring Bean으로 등록/관리됨
@RequiredArgsConstructor
public class MemberRepository {
	
	private final EntityManager em; //JPA 제공 표준 API - Spring이 해당 EntityManager를 만들어서 Injection 해줌
	
	/* JPA가 Member 객체를 저장하는 메서드 */
	public void save(Member member) {
		/* persistence(영속성) Context에 Member객체를 올리고, key : PrimaryKey(id)를 생성해줌
		 * -> 그래서 MemberService의 join메서드에서 member.getId()를 통해 값을 얻어오는게 보장됨 
		 */
		em.persist(member); 
	}
	
	/* 주어진 id에 해당하는 하나의 Member 객체를 Search하는 메서드 */
	public Member findOne(Long id) {
		Member member = em.find(Member.class, id);
		return member;
	}
	
	/* DB에 저장되어 있는 Member 객체 전부를 Search하는 메서드 */
	public List<Member> findAll(){
		List<Member> result = em.createQuery("select m from Memmber m", Member.class).getResultList(); //JPQL 문법?
		return result;
	}
	
	/* Name으로 Member를 Search하는 메서드 */
	public List<Member> findByName(String name){
		List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name",name).getResultList();
		return result;
	}
}











