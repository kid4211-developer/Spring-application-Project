package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired 
	OrderService orderService;
	
	@Autowired 
	OrderRepository orderRepository;
	
	@Test
	public void ��ǰ�ֹ�() throws Exception {
		
		//Given
		Member member = createMember();
		Item item = createBook("�ð� JPA", 10000, 10); //�̸�, ����, ���
		int orderCount = 2;
		
		//When
		Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
		
		//Then
		Order getOrder = orderRepository.findOne(orderId);
		assertEquals("��ǰ �ֹ��� ���´� ORDER",OrderStatus.ORDER,getOrder.getStatus());
		assertEquals("�ֹ��� ��ǰ ���� ���� ��Ȯ�ؾ� �Ѵ�.",1,getOrder.getOrderItems().size());
		assertEquals("�ֹ� ������ ���� * �����̴�.", 10000 * 2,getOrder.getTotalPrice());
		assertEquals("�ֹ� ������ŭ ��� �پ�� �Ѵ�.", 8, item.getStockQuantity());
	}
	
	@Test(expected = NotEnoughStockException.class)
	public void ��ǰ�ֹ�_�������ʰ�() throws Exception {
		
		//Given
		Member member = createMember();
		Item item = createBook("�ð� JPA", 10000, 10); //�̸�, ����, ���
		int orderCount = 11; //����� ���� ����
		
		//When
		orderService.order(member.getId(), item.getId(), orderCount);
		
		//Then
		fail("��� ���� ���� ���ܰ� �߻��ؾ� �Ѵ�.");
	}
	
	@Test
	public void �ֹ����() {
		
		//Given
		Member member = createMember();
		Item item = createBook("�ð� JPA", 10000, 10); //�̸�, ����, ���
		int orderCount = 2;
		Long orderId = orderService.order(member.getId(), item.getId(),	orderCount);
		
		//When
		orderService.cancelOrder(orderId);
		
		//Then
		Order getOrder = orderRepository.findOne(orderId);
		assertEquals("�ֹ� ��ҽ� ���´� CANCEL �̴�.",OrderStatus.CANCEL, getOrder.getStatus());
		assertEquals("�ֹ��� ��ҵ� ��ǰ�� �׸�ŭ ��� �����ؾ� �Ѵ�.", 10, item.getStockQuantity());
	}
	
	private Member createMember() {
		Member member = new Member();
		member.setName("ȸ��1");
		member.setAddress(new Address("����", "����", "123-123"));
		em.persist(member);
		return member;
	}
	
	private Book createBook(String name, int price, int stockQuantity) {
		Book book = new Book();
		book.setName(name);
		book.setStockQuantity(stockQuantity);
		book.setPrice(price);
		em.persist(book);
		return book;
	}
}