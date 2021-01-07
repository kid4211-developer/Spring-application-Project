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
	
	/* 주문 */
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {
		
		/* 엔티티 조회 */
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);
		
		/* 배송정보 생성 */
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());
		
		/* 주문 상품 생성 */
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
		
		/* 주문 생성 */
		Order order = Order.createOrder(member, delivery, orderItem);
		
		/* 주문 저장 - cascade=CascadeType.ALL 옵션에 의해 order만 persist해도 delivery와 orderItem도 persist된다
		 * order와 delivery / orderItem은 Lifecycle을 같이하기 때문에 cascade로 한데 묶는게 효율이 좋음
		 */
		orderRepository.save(order);
		
		return order.getId(); //order의 식별자값 반환
	}
	
	
	/* 주문 취소*/
	@Transactional
	public void cancelOrder(Long orderId) {
		
		/* 주문 엔티티 조회 */
		Order order = orderRepository.findOne(orderId);
		
		/* 주문 취소 */
		order.cancel();
	}
	
	/* 주문 검색 */
//	public List<Order> findOrders(OrderSearch ordersearch){
//		return orderRepository.findAll(orderSearch);
//	}
	
}
