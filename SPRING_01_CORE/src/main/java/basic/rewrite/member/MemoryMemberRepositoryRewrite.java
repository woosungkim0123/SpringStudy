package basic.rewrite.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemoryMemberRepositoryRewrite implements MemberRepositoryRewrite {

    private static Map<Long, MemberRewrite> store = new HashMap<>();


    @Override
    public void save(MemberRewrite member) {
        store.put(member.getId(), member);
    }

    @Override
    public MemberRewrite findById(Long memberId) {
        return store.get(memberId);
    }
}
