package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    // /hello로 들어오면 이 메서드를 호출해줌
    @GetMapping("hello")
    public String hello(Model model) {
        // spring이 model을 만들어서 넣어줌
        model.addAttribute("data", "hello!");
        return "hello";
        // 템플릿에 있는 hello를 찾아서 데이터를 화면에 넘기면서 이 화면을 렌더링 시켜라
    }

    @GetMapping("hello-mvc")
    // required 기본이 true required = false로 하면 안넘겨도됨
    // ctrl + p
    // /hello-mvc?name=hi
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("data1", name);
        return "hello1";
    }
    @GetMapping("hello-spring")
    // 의미: http 응답 body부에 데이터를 직접 넣어주겠다라는 의미
    @ResponseBody
    public String helloSpring(@RequestParam("name") String name) {
        return "body로 글자만 " + name; // "hello spring"
        // 문자가 그대로 내려감
    }
    // params
    @GetMapping("hello/{test}")
    public String test(@PathVariable("test") String test) {
        System.out.println("testest"+test);
        return "hello";
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    // static클래스로 만들면 클래스 안에서 클래스를 사용할 수 있음
    // HelloController.Hello 로 사용가능(정식으로 지원하는 문법)

    static class Hello {
        private String name;

        // 꺼낼때는 getName
        public String getName() {
            return name;
        }
        // 넣을때는 setName
        public void setName(String name) {
            this.name = name;
        }
    }

}
