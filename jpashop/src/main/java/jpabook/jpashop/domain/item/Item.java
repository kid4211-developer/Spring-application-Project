package jpabook.jpashop.domain.item;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)// InheritanceType은 총 3가지 타입이 있음 : SINGLE_TABLE, TABLE_PER_CLASS, JOINED */
@DiscriminatorColumn(name="dtype")
@Getter
@Setter
public abstract class Item { // 구현체를 따로 가지므로 추상클래스로 선언해줌(구현체들과는 상속관계 mapping을 해줘야함)
	
	@Id @GeneratedValue
	@Column(name="order_item")
	private Long id;
	
	private String name;
	
	private int price;
	
	private int stockQuantity;

	@ManyToMany(mappedBy="items")
	private List<Category> categories = new ArrayList<>();
	
	/* 비즈니스 로직 : stock 증가 */
	public void addStock(int quantity) {
		this.stockQuantity += quantity;
	}
	
	/* 비즈니스 로직 : stock 감소 */
	public void removeStock(int quantity) {
		 int restStock = this.stockQuantity - quantity;
		 if (restStock < 0) {
			 throw new NotEnoughStockException("need more stock");
		 }
		 this.stockQuantity = restStock;
	}
	
}








