package basic.rewrite.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceRewriteImpl implements MemberServiceRewrite {


    private final MemberRepositoryRewrite memberRepositoryRewrite;

    @Autowired
    public MemberServiceRewriteImpl(MemberRepositoryRewrite memberRepositoryRewrite) {
        this.memberRepositoryRewrite = memberRepositoryRewrite;
    }


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
