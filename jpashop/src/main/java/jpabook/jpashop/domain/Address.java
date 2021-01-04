package jpabook.jpashop.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable //내장이 될수 있음
@Getter
public class Address {
	/* 값타입의 경우 변경이 불가능하게 설계 되어야함 -> No Setter 
	 * JPA 스펙상 Entity나 임베디드 타입(@Embeddable)은 자바 기본 생성자(default constructor)를 public or protected로 설정해야 한다.
	 * public 으로 두는 것 보다는 protected 로 설정하는 것이 그나마 더 안전함
	 * JPA 구현 라이브러리가 객체를 생성할 때 리플랙션 같은 기술을 사용할 수 있도록 지원해야 하기 때문 */
	private String city;
	private String street;
	private String zipcode;
	
	protected Address() {}
	
	public Address(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
}
