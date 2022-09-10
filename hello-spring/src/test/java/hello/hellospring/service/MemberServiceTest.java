package hello.hellospring.service;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService = new MemberService();

    // 테스트는 한글로 바꿔도됨
    @Test
    void 회원가입() {
        // 주석 달아 놓으면 도움 많이됨
        // given
        Member member = new Member();
        member.setName("hello");

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
        Long saveId = memberService.join(member2);
        // try catch로 잡을 수도 있음
        try {

        } catch (IllegalAccessError e) {

        }
        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }


    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}