package jpabook.jpashop.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) //�⺻���� �б�޼���� readOnly�� true�� ��������� ������ ����ȭ��, join�� ���� ����޼���� ���� @Transactional�� �������� 
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	/* RequiredArgsConstructor Annotation�� ���� �Ʒ��� ���� Injection ó���� �ڵ����� ���� (�ݵ�� final�� �����������)
	 * ������ Injection�� �ϰԵǸ� MemberService�� �����ϴ� �ܰ迡�� MemberRepository�� �����Ǳ� ������ �߰��� ����� ���赵����
	 * test case �ۼ� �ܰ迡���� MemberService�� MemberRepository �������� �������ְ� �ľ��� �� ����
	@Autowired 
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	*/
	
	/* ȸ�� ���� */
	@Transactional //readOnly�� default���� false 
	public Long join(Member member) {
		validateDuplicateMember(member); 
		memberRepository.save(member);
		return member.getId();
	}
	
	/* �ߺ� ȸ�� ���� �޼��� - �߰����� üũ�������� unique ���������� ���̺� �ɾ��ִ� ���� ���� */
	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepository.findByName(member.getName());
		if(!findMembers.isEmpty()) {
			throw new IllegalStateException("�̹� �����ϴ� ȸ���Դϴ�.");
		}
	}
	
	/* ȸ�� ��ü ��ȸ */
	public List<Member> finMembers(){
		return memberRepository.findAll();
	}
	
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
}
