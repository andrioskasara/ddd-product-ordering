package mk.ukim.finki.emt.ordermanagement.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.ordermanagement.domain.valueobjects.Product;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
public class Order extends AbstractEntity<OrderId> {
    private Instant orderDate;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @Column(name = "order_currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItems = new HashSet<>();

    private Order() {
        super(OrderId.randomId(OrderId.class));
    }
    public Order(Instant now, @NotNull Currency currency) {
        super(OrderId.randomId(OrderId.class));
        this.orderDate = now;
        this.currency = currency;
    }

    public Money totalPrice() {
        return orderItems.stream()
                         .map(OrderItem::subtotal)
                         .reduce(new Money(currency, 0), Money::add);
    }

    public OrderItem addItem(@NonNull Product product, int qty) {
        Objects.requireNonNull(product, "product must not be null");
        var item = new OrderItem(product.getId(), product.getPrice(), qty);
        orderItems.add(item);
        return item;
    }

    public void removeItem(@NonNull OrderItemId orderItemId) {
        Objects.requireNonNull(orderItemId, "order item must not be null");
        orderItems.removeIf(i -> i.getId().equals(orderItemId));
    }
}
