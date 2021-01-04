package jpabook.jpashop.domain.item;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)// InheritanceType�� �� 3���� Ÿ���� ���� : SINGLE_TABLE, TABLE_PER_CLASS, JOINED */
@DiscriminatorColumn(name="dtype")
@Getter
@Setter
public abstract class Item { // ����ü�� ���� �����Ƿ� �߻�Ŭ������ ��������(����ü����� ��Ӱ��� mapping�� �������)
	
	@Id @GeneratedValue
	@Column(name="order_item")
	private Long id;
	
	private String name;
	
	private int price;
	
	private int stockQuantity;

	@ManyToMany(mappedBy="items")
	private List<Category> categories = new ArrayList<>();
}
