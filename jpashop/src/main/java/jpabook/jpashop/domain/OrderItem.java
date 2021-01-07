package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //타클래스에서 임의의 객체 생성을 막아줌
public class OrderItem {
	@Id @GeneratedValue
	@Column(name = "order_item_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="item_id")
	private Item item;
	
	@ManyToOne(fetch = FetchType.LAZY) // 하나의 Order는 여러개의 OrderItem을 가질수 있음 : 다대일
	@JoinColumn(name="order_id")
	private Order order;
	
	private int orderPrice; //주문 가격
	private int count; //주문 수량
	
	/* 생성메서드 - 주문아이템 생성 */
	public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);
		
		item.removeStock(count);
		return orderItem;
	}
	
	/* 비지니스 로직 - 주문아이템 삭제(재고수량 복구) */
	public void cancel() {
		getItem().addStock(count); //해당 item을 주문했던 수량만큼 재고에 다시 add 시켜줌 
		
	}
	
	/* 조회 로직 - 주문아이템 총 주문가격 조회 */
	public int getTotalPrice() {
		int itemTotalPrice = getOrderPrice() * getCount();
		return itemTotalPrice;
	}
}
