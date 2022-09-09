package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FirstController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!");
        return "hello";
    }

    @GetMapping("query")
    public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("data", name);
        return "hello";
    }
    // required 기본이 true required = false 하면 안넘겨도됨
    // ctrl + p : 인자 값이 무엇이 있는지 알아보는 키

    @GetMapping("body")
    @ResponseBody // 의미: http 응답 body에 데이터를 직접 넣어주겠다라는 의미
    public String helloSpring(@RequestParam("name") String name) {
        return "(문자열) 이름은? " + name;
    }
    // params
    @GetMapping("param/{test}")
    @ResponseBody
    public String test(@PathVariable("test") String test) {
        System.out.println("test에 들어오는 글자는 " + test);
        return test;
    }

    // api 방식
    @GetMapping("api/hello")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // 객체가 오면 JSON(default)으로 던짐
    }

    // static 클래스로 만들면 클래스 안에서 클래스를 사용할 수 있음
    // HelloController.Hello 로 사용가능(정식으로 지원하는 문법)

    static class Hello {
        private String name;
        /*
            getter, setter 단축키 : alt + insert
            자바빈 규약
            private이라 name을 바로 못꺼냄 그래서 메서드를 통해서 접근하게됨(프로퍼티 접근방식)

            JSON으로 변경할 때 기본으로 필드를 참고하는 것이 아니라 자바빈 프로퍼티 방식 명명 방식을 사용
            예를 들어서 getName(), setName()은 name이 프로퍼티 이름이 되는 것
            그래서 getTest는 test:value가 됨
        */
        public String getName() { // 꺼낼때는 getName
            return name;
        }
        public void setName(String name) { // 넣을때는 setName
            this.name = name;
        }
    }

}
