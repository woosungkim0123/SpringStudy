package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
/*
    Optional
    1. 자바8에 추가 된 기능
    2. 값을 가지고 올 때 값이 없으면 null, null을 그냥 반환하는 대신 Optional로 감싸서 반환하는 걸 선호
*/

