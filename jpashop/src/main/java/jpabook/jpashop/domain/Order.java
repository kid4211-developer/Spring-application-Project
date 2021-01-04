package jpabook.jpashop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	@ManyToOne
	@JoinColumn(name="member_id") // foreign Key 설정
	private Member member;
	
	@OneToMany(mappedBy="order")
	private List<OrderItem> orderItems = new ArrayList<>();
	
	private Delivery delivery;
	
	private LocalDateTime orderDate; // 주문 시간(java8 부터는 Hibernate가 자동 지원해줌)
	
	private OrderStatus status; // 주문의 사용 : [ORDER, CANCEL]
}
