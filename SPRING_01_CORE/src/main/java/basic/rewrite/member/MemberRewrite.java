package basic.rewrite.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class MemberRewrite {

    private Long id;
    private String name;
    private GradeRewrite grade;

    public MemberRewrite(Long id, String name, GradeRewrite grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

}
