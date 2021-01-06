package jpabook.jpashop.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) //기본적인 읽기메서드는 readOnly를 true로 셋팅해줘야 성능이 최적화됨, join과 같은 쓰기메서드는 재차 @Transactional를 선언해줌 
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	/* RequiredArgsConstructor Annotation을 통해 아래와 같은 Injection 처리를 자동으로 해줌 (반드시 final로 선언해줘야함)
	 * 생성자 Injection을 하게되면 MemberService을 생성하는 단계에서 MemberRepository도 결정되기 때문에 중간에 변경될 위험도없음
	 * test case 작성 단계에서도 MemberService의 MemberRepository 의존성을 직관성있게 파악할 수 있음
	@Autowired 
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	*/
	
	/* 회원 가입 */
	@Transactional //readOnly의 default값은 false 
	public Long join(Member member) {
		validateDuplicateMember(member); 
		memberRepository.save(member);
		return member.getId();
	}
	
	/* 중복 회원 검증 메서드 - 추가적인 체크수단으로 unique 제약조건을 테이블에 걸아주는 것이 좋음 */
	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepository.findByName(member.getName());
		if(!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}
	
	/* 회원 전체 조회 */
	public List<Member> finMembers(){
		return memberRepository.findAll();
	}
	
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
}
