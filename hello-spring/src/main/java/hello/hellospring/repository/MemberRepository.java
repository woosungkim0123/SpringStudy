package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    // 회원을 저장하면 저장된 회원이 반환
    Member save(Member member);
    // optional은 자바8에 추가 된 기능
    // 방금 id로 회원을 찾는 것
    Optional<Member> findById(Long id);


}
