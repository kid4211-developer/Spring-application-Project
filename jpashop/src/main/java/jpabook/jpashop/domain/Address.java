package jpabook.jpashop.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable //내장이 될수 있음
@Getter
public class Address {
	private String city;
	private String street;
	private String zipcode;
}
