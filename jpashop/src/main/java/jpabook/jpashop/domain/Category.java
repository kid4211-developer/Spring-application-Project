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
	
	/* �ǹ������� @ManyToMany �� ������� �ʴ°��� ���� 
	 * @ManyToMany�� ���� �� ������, �߰� ���̺�( CATEGORY_ITEM )�� �÷��� �߰��� �� ����, �����ϰ� ������ �����ϱ� ��Ʊ� ������ �ǹ����� ����ϱ⿡�� �Ѱ谡 �ִ�
	 * �׷��� �߰� Entity(CategoryItem)�� ����� @ManyToOne , @OneToMany�� �����ؼ� ����ϴ� ���� ����.
	 * �����ϸ� �ٴ�� ������ �ϴ��, �ٴ��� �������� Ǯ��� ����ؾ��� */
	// Category�� List���� �ٴ�� ���� ����
	// ������DB�� collection���踦 ���ʿ��� ������ ����(�ϴ�� �ٴ��Ϸ� Ǯ��� �ִ� �߰� �Ű������� table�� �߰��������)
	@ManyToMany	
	@JoinTable(name="category_item",
	           joinColumns = @JoinColumn(name="category_id"),
	           inverseJoinColumns=@JoinColumn(name="item_id")) 
	private List<Item> items = new ArrayList<>();
	
	/* Parent - Child : ���� ���� ���� */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parent_id")
	private Category parent;

	@OneToMany(mappedBy="parent")
	private List<Category> child = new ArrayList<>();
	
	/* �������� �޼��� */
	public void addChildCategory(Category child) {
		this.child.add(child);
		child.setParent(this);
	}
}
