package basic.rewrite.member;

public class MemberRewrite {

    private Long id;
    private String name;
    private GradeRewrite grade;

    public MemberRewrite(Long id, String name, GradeRewrite grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GradeRewrite getGrade() {
        return grade;
    }

    public void setGrade(GradeRewrite grade) {
        this.grade = grade;
    }
}
