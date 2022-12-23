package basic.rewrite.member;

public class MemberServiceRewriteImpl implements MemberServiceRewrite {

    private final MemberRepositoryRewrite memberRepositoryRewrite = new MemoryMemberRepositoryRewrite();

    @Override
    public void join(MemberRewrite memberRewrite) {
        memberRepositoryRewrite.save(memberRewrite);
    }

    @Override
    public MemberRewrite findById(Long memberId) {
        return memberRepositoryRewrite.findById(memberId);
    }
}
