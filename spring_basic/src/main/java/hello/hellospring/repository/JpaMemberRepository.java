package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;

    // jpa는 엔티티 매니저로 모든걸 동작.
    // data-jpa 라이브러리를 받으면 spring boot가 자동으로 엔티티 매니저를 생성해서(데이터 베이스를 연결해서) 만들어진 것을 injection 받으면 됨
   public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    // Pk의 경우우
   @Override
    public Optional<Member> findById(Long id) {
       Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    // JPQL 쿼리언어
    // 테이블 대상이 아닌 객체를 대상으로 쿼리를 날리면 SQL로 번역
    // 멤버 엔티티를 조회해
    // m은 as(allias), select의 대상은 멤버 엔티티 자체를 셀렉트
   @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // jpa를 사용하려면 주의해야할 점 : 항상 @Transactional이 필요함(서비스에 넣음), 데이터를 저장하거나 변경할 때 필요
    // jpa는 인터페이스고 자동으로 hibernate가 구현체 사용
}
