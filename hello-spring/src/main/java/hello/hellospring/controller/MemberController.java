package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
    private final MemberService memberService;


    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    // 생성자로 연결, 거기다 Autowired 해주면
    // 멤버 컨트롤러를 spirng 컨테이너 뜰때 생성하는데  그 때 생성자를 호출하는데 생성자에 autowired가 있으면 멤버서비스를 스프링이 스프링
    // 컨테이너 멤버 서비스를 가져다가 연결해줌
}
