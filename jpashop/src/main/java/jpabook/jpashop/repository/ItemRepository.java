package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
	
	private final EntityManager em;
	
	public void save(Item item) {
		/* JPA에 item을 저장하기 전까지는 id 값이 없음, 즉 id가 없다 = 새로 생성한 객체
		 * save()는 item 객체를 업데이트 해주는 역할을 함
		 */
		if (item.getId() == null) {
			em.persist(item);
		} else {
			em.merge(item);
		}
	}
	
	public Item findOne(Long id) {
		return em.find(Item.class, id);
	}
	
	public List<Item> findAll() {
		return em.createQuery("select i from Item i",Item.class).getResultList();
	}
}
