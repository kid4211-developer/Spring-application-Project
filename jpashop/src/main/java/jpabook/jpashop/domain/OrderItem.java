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
@NoArgsConstructor(access = AccessLevel.PROTECTED) //ŸŬ�������� ������ ��ü ������ ������
public class OrderItem {
	@Id @GeneratedValue
	@Column(name = "order_item_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="item_id")
	private Item item;
	
	@ManyToOne(fetch = FetchType.LAZY) // �ϳ��� Order�� �������� OrderItem�� ������ ���� : �ٴ���
	@JoinColumn(name="order_id")
	private Order order;
	
	private int orderPrice; //�ֹ� ����
	private int count; //�ֹ� ����
	
	/* �����޼��� - �ֹ������� ���� */
	public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);
		
		item.removeStock(count);
		return orderItem;
	}
	
	/* �����Ͻ� ���� - �ֹ������� ����(������ ����) */
	public void cancel() {
		getItem().addStock(count); //�ش� item�� �ֹ��ߴ� ������ŭ ��� �ٽ� add ������ 
		
	}
	
	/* ��ȸ ���� - �ֹ������� �� �ֹ����� ��ȸ */
	public int getTotalPrice() {
		int itemTotalPrice = getOrderPrice() * getCount();
		return itemTotalPrice;
	}
}
