package jpabook.jpashop.service;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
//@Rollback(false) test �ܰ迡�� Rollback�� �����ʰ� �������� - DB�� Data�� �������� �ԷµǴ��� �˼�����
public class MemberServiceTest {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Test
	public void ȸ������() throws Exception {
		//Given
		Member member = new Member();
		member.setName("jim");
		
		//When
		Long saveId = memberService.join(member);
		
		//Then
		assertEquals(member, memberRepository.findOne(saveId));
	}
	
	@Test(expected = IllegalStateException.class)
	public void �ߺ�_ȸ��_����() throws Exception {
		//Given
		Member member1 = new Member();
		member1.setName("kim");
		Member member2 = new Member();
		member2.setName("kim");
		
		//When
		memberService.join(member1);
		memberService.join(member2); //���ܰ� �߻��ؾ� �Ѵ�.
		
		//Then
		fail("���ܰ� �߻��ؾ� �Ѵ�.");
	}
}