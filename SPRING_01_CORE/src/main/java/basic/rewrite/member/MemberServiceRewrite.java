package basic.rewrite.member;

public interface MemberServiceRewrite {

    void join(MemberRewrite memberRewrite);

    MemberRewrite findById(Long memberId);
}
