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
@Table(name="orders") // table�� ��������
@Getter @Setter
public class Order {
	@Id @GeneratedValue
	@Column(name="order_id") // Column�� : tableName_id
	private Long id;

	/* Member���� ���� ����(Order�� ���� : �ٴ��� ����) */
	@ManyToOne
	@JoinColumn(name="member_id") // foreign Key ����
	private Member member;
	
	@OneToMany(mappedBy="order")
	private List<OrderItem> orderItems = new ArrayList<>();
	
	private Delivery delivery;
	
	private LocalDateTime orderDate; // �ֹ� �ð�(java8 ���ʹ� Hibernate�� �ڵ� ��������)
	
	private OrderStatus status; // �ֹ��� ��� : [ORDER, CANCEL]
}
