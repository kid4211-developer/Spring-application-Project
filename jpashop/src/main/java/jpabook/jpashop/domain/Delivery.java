package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {
	
	@Id @GeneratedValue
	@Column(name="delivery_id")
	private Long id;
	
	@OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
	private Order order;
	
	@Embedded
	private Address address;
	
	@Enumerated(EnumType.STRING) //중간에 상태값을 추가하더라도 밀리는 현상이 없기위해 반드시 EnumType은 String으로 선언해준다. ORDINAL : 가본타입이 숫자
    private DeliveryStatus status; //READY, COMP
}
