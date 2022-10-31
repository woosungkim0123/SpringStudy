package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/*
컨트롤러를 만들어두면 스프링이 처음 실행될 때 스프링 컨테이너랑 스프링 톰이 생기는데 거기에
컨트롤러 어노테이션이 있으면 멤버 컨트롤러 객체를 생성해서 스프링에 넣어둠 그리고 관리함
=> 스프링 컨테이너에서 스프링 빈이 관리된다. 라고 표현
 */

@Controller
public class MemberController {

    // 여기에 private로 서비스 객체를 생성해도됨 그러나 스프링이 관리를 하게되면 스프링 컨테이너에 등록을하고 스프링 컨테이너로 부터 받아서 쓰도록
    // 바꿔야함
    // 왜냐하면 new로 하면 문제가 멤버 컨트롤러말고 여러 컨트롤러들이 멤버 서비스를 가져다 쓸 수 있는데(주문 컨트롤러 등) 여러개 인스턴스를
    // 생성할 필요가 없음 하나만 생성해놓고 공용으로
    // 스프링 컨테이너에 등록을 하고 사용하는 방식으로 (딱 하나 등록)

    // 스프링 빈을 컨테이너에 등록할 때 기본적으로 싱글톤 사용
    // 싱글톤 = 유일하게 하나만 등록
    // 필드 주입(별로 안좋음)
    // 바꿀수 있는 방법이 없음 스프링 뜰 때만 넣어주고 중간에 바꿀 수 있는 방법이 없음
    // @Autowired private MemberService memberService;
    //private final MemberService memberService;


    // 생성자 방식(요즘 권장방식)
//    @Autowired
//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }
    // 생성자로 연결, 거기다 Autowired 해주면
    // 멤버 컨트롤러를 spirng 컨테이너 뜰때 생성하는데  그 때 생성자를 호출하는데 생성자에 autowired가 있으면 멤버서비스를 스프링이 스프링
    // 컨테이너 멤버 서비스를 가져다가 연결해줌

    // 생성자방식이 좋은 이유
    // 처음에 어플리케이션이 조립될 때 즉, 스프링 컨테이너 올라가고 세팅되는 시점에 한번들어오고 끝남. 그 이후에는 변경이 불가능 하도록

    // setter 주입
    // 단점 : 누군가가 멤버 컨트롤러 호출시 public으로 열려있어야함. 한번 설정하면 바꿀 일이 없는데도 public으로 노출
    // 아무 개발자나 호출할 수 있도록 열려있음(매우 위험)
    private MemberService memberService;

    @Autowired
    public void setMemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
