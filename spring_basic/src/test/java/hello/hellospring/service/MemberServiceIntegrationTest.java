package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

// 통합테스트

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    // 테스트 케이스는 테스트를 하기위한 용도라 편하게 사용(생성자 안사용햇음)
    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;

    // @Transactional 때문에 초기화 코드가 필요없음
    // db에는 트랜잭션이라는 개념이 있는데 커밋하기 전까지 반영이 안됨, 즉, 롤백 시켜버린거

    @Test
    void 회원가입() {
        // 주석 달아 놓으면 도움 많이됨
        // given
        Member member = new Member();
        member.setName("spring1");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    @Test
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("woosung");
        Member member2 = new Member();
        member2.setName("woosung");
        // when
        memberService.join(member1);

        // then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }


    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}