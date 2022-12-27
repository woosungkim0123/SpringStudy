package basic.rewrite.member;

import basic.core.member.MemberRepository;

public class MemberServiceRewriteImpl implements MemberServiceRewrite {


    private final MemberRepositoryRewrite memberRepositoryRewrite;

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
