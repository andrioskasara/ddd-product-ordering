package mk.ukim.finki.emt.ordermanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

@Getter
public class Product implements ValueObject {
    private final ProductId id;
    private final String name;
    private final String description;
    private final Money price;
    private final int sales;


    private Product() {
        this.id = ProductId.randomId(ProductId.class);
        this.name = "";
        this.description = "";
        this.price = Money.valueOf(Currency.MKD, 0.0);
        this.sales = 0;
    }

    @JsonCreator
    public Product(@JsonProperty("id") ProductId id,
                   @JsonProperty("productName") String name,
                   @JsonProperty("productDescription") String description,
                   @JsonProperty("productPrice") Money price,
                   @JsonProperty("sales") int sales) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sales = sales;
        this.price = price;
    }
}
