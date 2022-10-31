package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/*
    굳이 public으로 할 필요 없음(가져다 쓸 것이 아니라서)
*/
class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();


    // 메서드가 끝날때 마다 동작작
   @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("woosung");
        // 글 쓰는 도중에 다음줄로 엔터치고 싶으면 ctrl + shift + enter
        repository.save(member);

        // Optional에서 값을 꺼낼때는 get으로 바로 꺼내는 방법은 좋지는 않지만 테스트코드라
        Member result = repository.findById(member.getId()).get();
        // Assertions.assertEquals(member, result);

        // 다른 방법 편한방법 (요즘에 많이 사용)
        // alter + Enter => static import 시 줄일 수 있음
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("t1");
        repository.save(member1);

        // shift + f6으로 rename 가능능
        Member member2 = new Member();
        member2.setName("t2");
        repository.save(member2);

        //Optional<Member> t1 = repository.findByName("t1");
        Member result = repository.findByName("t1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("t1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("t2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);

        /*
        순서 보장안됨 => 순서랑 상관없이 동작하도록 설계
        테스트를 진행하고 나면 데이터를 클리어 해줘야함
       */
    }
}
