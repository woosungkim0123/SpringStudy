package book.shop.service;

import book.shop.domain.Member;
import book.shop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void join() {
        // given
        Member member = new Member();
        member.setName("우성");

        // when
        Long savedId = memberService.join(member);

        Optional<Member> optionalMember = memberRepository.findById(savedId);

        // then
        assertEquals(member, optionalMember.get());
    }

    @Test
    public void duplicateCheck() {
        // given
        Member member1 = new Member();
        member1.setName("우성");
        Member member2 = new Member();
        member2.setName("우성");

        // when
        System.out.println(" ================1 ");
        memberService.join(member1);

        // then
        System.out.println(" ================ 2");
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        System.out.println(" ================ 3");
    }
}