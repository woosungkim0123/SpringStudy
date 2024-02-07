package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // 규칙이있음
    // select m from Member m where m.name = ?
    // findByNameAndId(String name, Long id), or도 있음
    // 인터페이스 이름만으로도 개발을 끝낼 수 있음
    @Override
    Optional<Member> findByName(String name);
}
