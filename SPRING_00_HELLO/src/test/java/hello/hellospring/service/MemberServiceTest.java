package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemoryMemberRepository memberRepository;
    MemberService memberService;

    // 같은 MemoryMemberRepository를 사용하기 위해서 (다른 인스턴스로 생성되면 뭔가 다를수 있어서)
    // 외부에서 넣어줌 (의존성 주입 DI)
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    // 테스트는 한글로 바꿔도됨
    @Test
    void 회원가입() {
        // 주석 달아 놓으면 도움 많이됨
        // given
        Member member = new Member();
        member.setName("woosung");

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
        // 메세지 검증
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        // try catch로 잡을 수도 있음
        /*
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalAccessError e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/

        // shift+ f10 이전에 실행한걸 실행해줌


    }


    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}