package mk.ukim.finki.emt.ordermanagement.service.forms;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;

import java.util.ArrayList;
import java.util.List;


@Data
public class OrderForm {
    @NotNull
    private Currency currency;

    @Valid
    @NotEmpty
    private List<OrderItemForm> items = new ArrayList<>();
}
