package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class OrderItem {
	@Id @GeneratedValue
	@Column(name = "order_item_id")
	private Long id;
	
	private Item item;
	
	@ManyToOne // 하나의 Order는 여러개의 OrderItem을 가질수 있음 : 다대일
	@JoinColumn(name="order_id")
	private Order order;
	
	private int orderPrice;
	private int count;
}
