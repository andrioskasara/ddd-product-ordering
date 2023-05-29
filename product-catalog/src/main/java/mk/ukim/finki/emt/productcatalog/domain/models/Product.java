package mk.ukim.finki.emt.productcatalog.domain.models;


import jakarta.persistence.*;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;


@Entity
@Table(name = "product")
@Getter
public class Product extends AbstractEntity<ProductId> {
    private String productName;
    private String productDescription;
    private int sales = 0;
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "price_currency"))
    })
    private Money productPrice;

    private Product() {
        super(ProductId.randomId(ProductId.class));
    }

    public static Product build(String productName, String productDescription, Money productPrice, int sales){
        Product product = new Product();
        product.productName = productName;
        product.productDescription = productDescription;
        product.sales = sales;
        product.productPrice = productPrice;
        return product;
    }

    public void addSales(int qty) {
        this.sales += qty;
    }

    public void removeSales(int qty) {
        this.sales -= qty;
    }
}
