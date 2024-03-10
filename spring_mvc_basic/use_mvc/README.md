# 스프링 MVC 기본 기능

## 로깅

Logback, Log4J 등 다양한 로깅 라이브러리가 있고 이것들을 통합해서 인터페이스로 제공하는 것이 `SLF4J`입니다.

쉽게 말해 SLF4J는 인터페이스이고 그 구현체로 Logback 같은 라이브러리를 선택하면 됩니다. (스프링 부트는 기본적으로 Logback을 사용합니다.)

`private static final Logger log = LoggerFactory.getLogger(getClass());`, `@Slf4j`를 사용해서 로그를 사용할 수 있습니다.

<br>

## 매핑

스프링 컨트롤러는 다양한 파라미터를 받을 수 있습니다.

`HttpServletRequest`, `HttpServletResponse`, `Locale`, `@RequestHeader MultiValueMap<String, String>`, `@CookieValue(value="")`

### @RequestMapping

`@RequestMapping`을 사용해서 URL을 매핑할 수 있습니다.

`@GetMapping`, `@PostMapping`으로 대체할 수 있습니다.

### @RequestParam

`@RequestParam`을 사용해서 파라미터를 받을 수 있습니다. 

String, int,Integer 등의 단순 타입이면 @RequestParam 도 생략 가능합니다. 대신 `@RequestParam` 애노테이션을 생략하면 스프링 MVC는 내부에서 required=false 를 적용합니다. (기본값 설정도 가능)

```java
@Controller
public class HelloController {
    @GetMapping("/hello1")
    public ModelAndView hello1(
            @RequestParam("name") String name,
            @RequestParam("age") int age,
            Model model
    ) {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "hello1";
    }
}
```

### @PathVariable

경로변수의 경우 `@PathVariable`을 사용해서 받을 수 있습니다.

@PathVariable의 이름과 파라미터의 이름이 같으면 생략할 수 있습니다.

```java
@GetMapping("/hello/{name}")
public String hello(@PathVariable("name") String name, Model model) {
    model.addAttribute("name", name);
    return "hello";
}

// 생략 버전
@GetMapping("/hello/{name}")
public String hello(@PathVariable String name, Model model) {
    model.addAttribute("name", name);
    return "hello";
}
```

### @ModelAttribute

`@ModelAttribute`를 사용하면 파라미터를 받아서 객체로 만들어줍니다.

이때, `model.addAttribute(helloData)`를 생략해도 자동으로 모델에 추가됩니다.

```java
@ResponseBody
@RequestMapping("/model-attribute-v1")
public String modelAttributeV1(@ModelAttribute HelloData helloData) {
    log.info("username={}, age={}", helloData.getUsername(),
    helloData.getAge());
    return "ok";
}
```

`@ModelAttribute`는 생략이 가능합니다.

> 생략 가능 규칙
> 
> String, int 같은 단순 타입 = @RequestParam    
> 나머지 = @ModelAttribute (argument resolver로 지정된 타입은 생략 가능)  
> HttpServletRequest 같은 것들이 argument resolver로 지정되어 있습니다.

### @RequestBody, @ResponseBody

요청 파라미터와 다르게 HTTP 메세지 바디를 통해 데이터가 직접 전달되는 경우에는 `@ModelAttribute`, `@RequestParam`을 사용할 수 없습니다.

`@RequestBody`를 사용하면 HTTP 메세지 바디 정보를 조회할 수 있습니다.

`@RequestBody`, `@ResponseBody` 사용시 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가 원하는 문자나 객체 등으로 변환해줍니다. (JSON도 변환 가능)

```java
@ResponseBody
@PostMapping("/request-body-string-v4")
public String requestBodyStringV4(@RequestBody String messageBody) {
    log.info("messageBody={}", messageBody);
    return "ok";
}
```

`@ResponseBody`를 사용하면 응답 결과를 HTTP 메세지 바디에 직접 담아서 전달할 수 있습니다. (view를 사용하지 않음)









<br>

## 기타

- `/resources/static/` 위치에 `index.html` 파일을 두면 Welcome Page로 설정할 수 있습니다.
- view의 논리적 이름을 반환할 때 `ModelAndView`를 반환해도 되지만 그냥 `String`을 반환해도 됩니다.


