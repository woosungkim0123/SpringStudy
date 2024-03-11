# Validation

## 검증 방법

검증시 @Validated @Valid 둘다 사용가능 합니다.

@Validated 는 스프링 전용 검증 어노테이션이고 @Valid 는 자바 표준 검증 어노테이션입니다. (@Valid는 의존관계 추가가 필요합니다. 'org.springframework.boot:spring-boot-starter-validation')

> 등록, 수정용 뷰 템플릿이 비슷하더라도 분리해서 관리하는 것이 좋습니다.

### @RequestParam, @ModelAttribute 사용시

```java
@Data
public class ItemUpdateForm {
    @NotNull
    private Long id;
    
    @NotBlank
    private String itemName;
    
    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;
    
    private Integer quantity; // 수정에서는 수량은 자유롭게 변경할 수 있다.
}
```

```java
@PostMapping("/add")
public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    
    //특정 필드 예외가 아닌 전체 예외
    if (form.getPrice() != null && form.getQuantity() != null) {
        int resultPrice = form.getPrice() * form.getQuantity();
        if (resultPrice < 10000) {
            bindingResult.reject("totalPriceMin", new Object[]{10000,resultPrice}, null);
        }
    }
    
    if (bindingResult.hasErrors()) {
        log.info("errors={}", bindingResult);
        return "validation/v4/addForm"; // 다시 입력 폼으로
    }
    
    //성공 로직
    
    return "redirect:/validation/v4/items/{itemId}";
}
```

`@Validated`(또는 `@Valid`) 어노테이션을 사용하여 데이터 바인딩과 검증 과정을 거친 후, 검증에 실패한 경우에도 해당 컨트롤러 메서드는 호출됩니다. 

이때 `BindingResult` 객체에 검증 오류 정보가 담겨 있으며 뷰 템플릿으로 전달하는 Model에 자동으로 추가됩니다.

@Valid나 @Validated 어노테이션은 Spring MVC의 데이터 바인딩 과정이 성공적으로 완료된 후에 유효성 검사를 수행합니다

만약 클라이언트가 전송한 데이터의 타입이 메서드 파라미터의 타입과 맞지 않아서 바인딩 자체가 실패하는 경우(예: 문자열을 정수 타입 필드에 바인딩하려고 할 때,
Spring은 `MethodArgumentTypeMismatchException` 예외를 발생시키고 이때는 유효성 검사에 도달하기 전에 에러가 터졌기 때문에 로직이 실행되지 않습니다.

### HTTP 메세지 컨버터 사용시 (RequestBody)

실패는 크게 두가지로 나뉘는데 JSON 객체로 생성하는 것 자체가 실패하는 경우와 객체 생성은 성공했지만 검증에 실패하는 경우입니다.

먼저 JSON 객체로 생성하는 것 자체가 실패하는 경우는 `HttpMessageNotReadableException` 예외가 발생합니다.

객체를 만들지 못하기 때문에 컨트롤러 자체가 호출되지 않고 예외가 발생합니다.

검증 오류 실패는 컨트롤러가 호출되고 `bindingResult`에 오류가 담겨있습니다.

#### ModelAttribute vs RequestBody

`@ModelAttribute`는 필드 단위로 정교하게 바인딩이 적용됩니다. 

특정 필드가 바인딩 되지 않아도 나머지 필드는 정상 바인딩 되고, Validator를 사용한 검증도 적용할 수 있습니다.

`@RequestBody`는 HttpMessageConverter 단계에서 JSON 데이터를 객체로 변경하지 못하면 이후 단계 자체가 진행되지 않고 예외가 발생합니다.

컨트롤러도 호출되지 않고, Validator도 적용할 수 없습니다.
