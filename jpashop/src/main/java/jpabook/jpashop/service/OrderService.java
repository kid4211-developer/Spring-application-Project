package jpabook.jpashop.service;

import java.util.List;
import javax.persistence.CascadeType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;
	
	/* �ֹ� */
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {
		
		/* ��ƼƼ ��ȸ */
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);
		
		/* ������� ���� */
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());
		
		/* �ֹ� ��ǰ ���� */
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
		
		/* �ֹ� ���� */
		Order order = Order.createOrder(member, delivery, orderItem);
		
		/* �ֹ� ���� - cascade=CascadeType.ALL �ɼǿ� ���� order�� persist�ص� delivery�� orderItem�� persist�ȴ�
		 * order�� delivery / orderItem�� Lifecycle�� �����ϱ� ������ cascade�� �ѵ� ���°� ȿ���� ����
		 */
		orderRepository.save(order);
		
		return order.getId(); //order�� �ĺ��ڰ� ��ȯ
	}
	
	
	/* �ֹ� ���*/
	@Transactional
	public void cancelOrder(Long orderId) {
		
		/* �ֹ� ��ƼƼ ��ȸ */
		Order order = orderRepository.findOne(orderId);
		
		/* �ֹ� ��� */
		order.cancel();
	}
	
	/* �ֹ� �˻� */
//	public List<Order> findOrders(OrderSearch ordersearch){
//		return orderRepository.findAll(orderSearch);
//	}
	
}
