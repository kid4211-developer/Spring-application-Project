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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="orders") // table을 지정해줌
@Getter @Setter
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
	
}
