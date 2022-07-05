package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    // 어딘가 저장해야하니 Map사용
    private static Map<Long, Member> store = new HashMap<>();
    // sequence는 키값을 0,1,2 생성해주는 애라고 생각하면 됨
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        // sequence값을 하나 올려주고
        member.setId(++sequence);
        // store에 올리기전 id값을 세팅해주고
        store.put(member.getId(), member);
        return member;

    }

    @Override
    public Optional<Member> findById(Long id) {
        // 예전에는 return store.get(id);
        // 요즘에는 null이 반환될가능성이있으면 optional로 감쌈
        return Optional.ofNullable(store.get(id));
        // null이여도 감쌀수잇고 클라이언트에서 무언갈 할 수 있음
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 자바8 람다
        return store.values().stream()
                // 루프로 돌림
                // 파라미터로 넘어온 name이랑 같은지확인
                .filter(member -> member.getName().equals(name))
                // 같은 것만 필터됨
                .findAny(); // 하나라도 찾는 것
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
