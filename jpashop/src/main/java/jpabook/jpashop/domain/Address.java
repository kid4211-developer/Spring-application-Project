package jpabook.jpashop.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable //������ �ɼ� ����
@Getter
public class Address {
	/* ��Ÿ���� ��� ������ �Ұ����ϰ� ���� �Ǿ���� -> No Setter 
	 * JPA ����� Entity�� �Ӻ���� Ÿ��(@Embeddable)�� �ڹ� �⺻ ������(default constructor)�� public or protected�� �����ؾ� �Ѵ�.
	 * public ���� �δ� �� ���ٴ� protected �� �����ϴ� ���� �׳��� �� ������
	 * JPA ���� ���̺귯���� ��ü�� ������ �� ���÷��� ���� ����� ����� �� �ֵ��� �����ؾ� �ϱ� ���� */
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
