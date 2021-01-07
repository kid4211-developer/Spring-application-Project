package jpabook.jpashop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="orders") // table�� ��������
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
	@Id @GeneratedValue
	@Column(name="order_id") // Column�� : tableName_id
	private Long id;

	/* Member���� ���� ����(Order�� ���� : �ٴ��� ����) */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id") // foreign Key ����
	private Member member;
	
	@OneToMany(mappedBy="order", cascade=CascadeType.ALL) // CascadeType�� ALL�� ����Ǿ� ������ persist(order) �ѹ濡 �ذ��
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="delivery_id") // foreign Key ����, ���������� ���� : orders 
	private Delivery delivery;
	
	private LocalDateTime orderDate; // �ֹ� �ð�(java8 ���ʹ� Hibernate�� �ڵ� ��������)
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status; // �ֹ��� ���� : [ORDER, CANCEL]
	
	/* �������� ���� �޼��� - ���� ���踦 ���������� �ϳ��� �ڵ�� �ذ��ϴ� ��� */
	public void setMember(Member member) {
		this.member = member;
		member.getOrders().add(this);
	}
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
	
	public void setDelivery(Delivery delivery) {
		this.delivery=delivery;
		delivery.setOrder(this);
	}
	
	/* ���� �޼��� - �ֹ� ����
	 * �ֹ��� ���� ���°� ������ �ϰ� Order ��ü�� ��ȯ����
	 */
	public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) { //OrderItem�� List�� �ѱ�� �Ǵµ� �������ϼ� ������ orderItems�� ���)
		Order order = new Order();
		order.setMember(member);
		order.setDelivery(delivery);
		for(OrderItem orderItem : orderItems) {
			order.addOrderItem(orderItem);
		}
		order.setStatus(OrderStatus.ORDER); //Order�� ���¸� �켱 ORDER�� �����س���
		order.setOrderDate(LocalDateTime.now());
		return order;
	}
	
	/* �����Ͻ� ���� - �ֹ� ��� */
	public void cancel() {
		if (delivery.getStatus() == DeliveryStatus.COMP) {
			throw new IllegalStateException("�̹� ��ۿϷ�� ��ǰ�� ��Ұ� �Ұ����մϴ�.");
		}
		this.setStatus(OrderStatus.CANCEL); //����� �Ϸ�� ��ǰ�� �ƴ϶�� �ֹ����¸� ��ҷ� ��������
		for(OrderItem orderItem : orderItems) { //�ݺ����� ���� �� �ֹ� item�� ��� ���󺹱� ������
			orderItem.cancel(); //�ֹ��� �ش��ϴ� orderItem�� ���� ��� ������ϱ� ������ OrderItem���� cancel����� �����Ͻ� ������ �߰����� 
		}
	}
	
	/* ��ȸ ���� - ��ü �ֹ� �ݾ� ��ȸ */
	public int getTotalPrice() {
		int totalPrice = 0;
		for(OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		return totalPrice;
	}
}










