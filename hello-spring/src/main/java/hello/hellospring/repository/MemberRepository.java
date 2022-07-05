package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    // 회원을 저장하면 저장된 회원이 반환
    Member save(Member member);
    // 방금 id로 회원을 찾는 것
    // optional은 자바8에 추가 된 기능
    // findbyid나 findbyname을 가지고 올떄 null일 수 있는데(없으면)
    // 요즘은 null처리방법에서 null을 그냥 반환하는 방법대신에 Optional이라는걸로 감싸서 반환하는걸 선호함

    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();

}
