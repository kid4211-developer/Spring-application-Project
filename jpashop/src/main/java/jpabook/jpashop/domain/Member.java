package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {
	@Id @GeneratedValue
	@Column(name="member_id")
	private Long id;
	
	private String name;
	
	@Embedded
	private Address address;
	
	/* 한명의 Member가 여러 주문이 가능 : 일대다 관계 */
	@OneToMany(mappedBy = "member") //order table에 있는 member filed에 의해 mapping 됐음을 의미함(읽기전용 / Member와 Order에서 연관관계 주인은 Order임)
	private List<Order> orders = new ArrayList<>();
}
