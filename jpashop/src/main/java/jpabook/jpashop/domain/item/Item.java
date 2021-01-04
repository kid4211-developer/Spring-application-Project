package jpabook.jpashop.domain.item;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public abstract class Item { // ����ü�� ���� �����Ƿ� �߻�Ŭ������ ��������(����ü����� ��Ӱ��� mapping�� �������)
	
	@Id @GeneratedValue
	@Column(name="order_item")
	private Long id;
	
	private String name;
	
	private int price;
	
	private int stockQuantity;
}
