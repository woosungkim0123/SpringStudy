package hello.itemservice.web.validation.form;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemUpdateForm {

    @NotNull
    private Long id;

    @NotBlank
    private String itemName;

    @NonNull
    @Range(min = 1000, max=10000)
    private Integer price;

    private Integer quantity;
}
