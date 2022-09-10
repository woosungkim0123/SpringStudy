package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

// 클래스 테스트 만드는 단축키

public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 중복회원 X
        /*
        Optional<Member> result = memberRepository.findByName(member.getName());
        // Optional로 감싸서 사용가능
        // ifPresent는 null이 아니라 안에 값이 있으면 실행
        // 기존에는 ifnull아 아니면 이런식으로 짬
        // Optional로 감싸면 Optional안에 멤버객체가 존재하게됨. 그래서 Optional 통해서 여러 메서드를 사용할 수 있음
        // 요즘은 null일 가능성이 있으면 Optional 한번 감싸서 반환함
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
        */
        // 위 코드 리팩토링
        // 기능을 빼는 게 좋음 => 리팩토링 => 메서드 추출
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    // 레포지토리 클래스 이름은 개발스럽게
    // service 클래스는 비지니스에 가깝게

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
