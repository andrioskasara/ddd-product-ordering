package mk.ukim.finki.emt.ordermanagement.service.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderIdDoesNotExistException;
import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderItemIdDoesNotExistException;
import mk.ukim.finki.emt.ordermanagement.domain.models.Order;
import mk.ukim.finki.emt.ordermanagement.domain.models.OrderId;
import mk.ukim.finki.emt.ordermanagement.domain.models.OrderItemId;
import mk.ukim.finki.emt.ordermanagement.domain.repository.OrderRepository;
import mk.ukim.finki.emt.ordermanagement.service.OrderService;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderForm;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderItemForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderId placeOrder(OrderForm orderForm) {
        Objects.requireNonNull(orderForm, "order must not be null");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        var constraintViolations = validator.validate(orderForm);
        if (constraintViolations.size() > 0) {
            System.out.println("Constraint Violations occurred..");
            for (ConstraintViolation<OrderForm> constraints : constraintViolations) {
                System.out.println(constraints.getRootBeanClass().getSimpleName() +
                        "." + constraints.getPropertyPath() + " " + constraints.getMessage());
            }
        }
        var newOrder = this.orderRepository.saveAndFlush(toDomainObject(orderForm));
        return newOrder.getId();
    }

    @Override
    public List<Order> findAll() {
        return this.orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(OrderId orderId) throws OrderIdDoesNotExistException {
        return this.orderRepository.findById(orderId);
    }

    @Override
    public void addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderIdDoesNotExistException {
        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new OrderIdDoesNotExistException());
        order.addItem(orderItemForm.getProduct(), orderItemForm.getQuantity());
        this.orderRepository.saveAndFlush(order);
    }

    @Override
    public void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderIdDoesNotExistException, OrderItemIdDoesNotExistException {
        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new OrderIdDoesNotExistException());
        order.removeItem(orderItemId);
        this.orderRepository.saveAndFlush(order);
    }

    private Order toDomainObject(OrderForm orderForm) {
        var order = new Order(Instant.now(), orderForm.getCurrency());
        orderForm.getItems().forEach(item -> order.addItem(item.getProduct(), item.getQuantity()));
        return order;
    }
}
