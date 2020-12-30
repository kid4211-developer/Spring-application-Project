package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {
	@Id @GeneratedValue
	private Long id;
	
	private String name;
	
	private Address address;
	
	private List<Order> orders = new ArrayList<>();
}
