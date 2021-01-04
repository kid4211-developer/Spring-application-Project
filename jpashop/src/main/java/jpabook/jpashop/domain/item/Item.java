package jpabook.jpashop.domain.item;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public abstract class Item { // 구현체를 따로 가지므로 추상클래스로 선언해줌(구현체들과는 상속관계 mapping을 해줘야함)
	
	@Id @GeneratedValue
	@Column(name="order_item")
	private Long id;
	
	private String name;
	
	private int price;
	
	private int stockQuantity;
}
