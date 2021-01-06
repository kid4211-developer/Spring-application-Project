package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category {
	@Id @GeneratedValue
	@Column(name="category_id")
	private Long id;
	
	private String name;
	
	/* 실무에서는 @ManyToMany 를 사용하지 않는것이 좋음 
	 * @ManyToMany는 편리한 것 같지만, 중간 테이블( CATEGORY_ITEM )에 컬럼을 추가할 수 없고, 세밀하게 쿼리를 실행하기 어렵기 때문에 실무에서 사용하기에는 한계가 있다
	 * 그래서 중간 Entity(CategoryItem)를 만들고 @ManyToOne , @OneToMany로 매핑해서 사용하는 것이 좋음.
	 * 정리하면 다대다 매핑을 일대다, 다대일 매핑으로 풀어내서 사용해야함 */
	// Category와 List간에 다대다 관계 설정
	// 관계형DB는 collection관계를 양쪽에다 가질수 없음(일대다 다대일로 풀어낼수 있는 중간 매개역할의 table을 추가해줘야함)
	@ManyToMany	
	@JoinTable(name="category_item",
	           joinColumns = @JoinColumn(name="category_id"),
	           inverseJoinColumns=@JoinColumn(name="item_id")) 
	private List<Item> items = new ArrayList<>();
	
	/* Parent - Child : 셀프 연관 관계 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parent_id")
	private Category parent;

	@OneToMany(mappedBy="parent")
	private List<Category> child = new ArrayList<>();
	
	/* 연관관계 메서드 */
	public void addChildCategory(Category child) {
		this.child.add(child);
		child.setParent(this);
	}
}
