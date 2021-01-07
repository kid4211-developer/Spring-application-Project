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
@Table(name="orders") // table을 지정해줌
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
	@Id @GeneratedValue
	@Column(name="order_id") // Column명 : tableName_id
	private Long id;

	/* Member와의 관계 셋팅(Order의 입장 : 다대일 관계) */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id") // foreign Key 설정
	private Member member;
	
	@OneToMany(mappedBy="order", cascade=CascadeType.ALL) // CascadeType이 ALL로 선언되어 있으면 persist(order) 한방에 해결됨
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="delivery_id") // foreign Key 설정, 연관관계의 주인 : orders 
	private Delivery delivery;
	
	private LocalDateTime orderDate; // 주문 시간(java8 부터는 Hibernate가 자동 지원해줌)
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status; // 주문의 상태 : [ORDER, CANCEL]
	
	/* 연관관계 편의 메서드 - 양쪽 관계를 원자적으로 하나의 코드로 해결하는 방식 */
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
	
	/* 생성 메서드 - 주문 생성
	 * 주문에 대한 상태값 셋팅을 하고 Order 객체를 반환해줌
	 */
	public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) { //OrderItem은 List로 넘기게 되는데 여러개일수 있으니 orderItems로 명명)
		Order order = new Order();
		order.setMember(member);
		order.setDelivery(delivery);
		for(OrderItem orderItem : orderItems) {
			order.addOrderItem(orderItem);
		}
		order.setStatus(OrderStatus.ORDER); //Order의 상태를 우선 ORDER로 강제해놓음
		order.setOrderDate(LocalDateTime.now());
		return order;
	}
	
	/* 비지니스 로직 - 주문 취소 */
	public void cancel() {
		if (delivery.getStatus() == DeliveryStatus.COMP) {
			throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
		}
		this.setStatus(OrderStatus.CANCEL); //배송이 완료된 상품이 아니라면 주문상태를 취소로 변경해줌
		for(OrderItem orderItem : orderItems) { //반복문을 통해 각 주문 item의 재고를 원상복구 시켜줌
			orderItem.cancel(); //주문에 해당하는 orderItem도 각각 취소 해줘야하기 때문에 OrderItem에도 cancel기능의 비지니스 로직을 추가해줌 
		}
	}
	
	/* 조회 로직 - 전체 주문 금액 조회 */
	public int getTotalPrice() {
		int totalPrice = 0;
		for(OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		return totalPrice;
	}
}










