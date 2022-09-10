package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>(); // 어딘가 저장해야하니 Map사용
    private static long sequence = 0L;  // sequence는 키값을 0,1,2 생성해주는 애라고 생각하면 됨

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 예전 : return store.get(id);
        return Optional.ofNullable(store.get(id));
        // null이여도 감쌀 수 있고 감싸게 되면 클라이언트에서 무언갈 처리가능
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 자바8 람다
        return store.values().stream()
                // 루프로 돌림
                // 파라미터로 넘어온 name이랑 같은지 확인
                .filter(member -> member.getName().equals(name))
                .findAny(); // 하나라도 찾는 것
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
