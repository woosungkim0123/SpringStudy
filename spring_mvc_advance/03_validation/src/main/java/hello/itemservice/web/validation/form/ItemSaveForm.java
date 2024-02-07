package hello.itemservice.web.validation.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Range;


@Data
public class ItemSaveForm {

    @NotBlank
    private String itemName;

    @NonNull
    @Range(min = 1000, max=10000)
    private Integer price;

    @NotNull
    @Max(9999)
    private Integer quantity;
}
