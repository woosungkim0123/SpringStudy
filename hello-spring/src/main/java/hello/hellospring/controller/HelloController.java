package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}
