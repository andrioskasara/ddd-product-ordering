package mk.ukim.finki.emt.ordermanagement.service.forms;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mk.ukim.finki.emt.ordermanagement.domain.valueobjects.Product;


@Data
public class OrderItemForm {
    @NotNull
    private Product product;

    @Min(1)
    private int quantity = 1;
}
