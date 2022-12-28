package basic.rewrite.member;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberServiceRewriteImpl implements MemberServiceRewrite {

    private final MemberRepositoryRewrite memberRepositoryRewrite;

    @Override
    public void join(MemberRewrite memberRewrite) {
        memberRepositoryRewrite.save(memberRewrite);
    }

    @Override
    public MemberRewrite findById(Long memberId) {
        return memberRepositoryRewrite.findById(memberId);
    }

    public MemberRepositoryRewrite getMemberRepository() {
        return memberRepositoryRewrite;
    }
}
