package basic.rewrite.member;

public interface MemberRepositoryRewrite {

    void save(MemberRewrite member);

    MemberRewrite findById(Long memberId);
}
