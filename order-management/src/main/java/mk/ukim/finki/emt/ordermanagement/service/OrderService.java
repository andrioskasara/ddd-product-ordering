package mk.ukim.finki.emt.ordermanagement.service;

import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderIdDoesNotExistException;
import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderItemIdDoesNotExistException;
import mk.ukim.finki.emt.ordermanagement.domain.models.Order;
import mk.ukim.finki.emt.ordermanagement.domain.models.OrderId;
import mk.ukim.finki.emt.ordermanagement.domain.models.OrderItemId;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderForm;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderItemForm;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderId placeOrder(OrderForm orderForm);

    List<Order> findAll();

    Optional<Order> findById(OrderId orderId) throws OrderIdDoesNotExistException;

    void addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderIdDoesNotExistException;

    void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderIdDoesNotExistException, OrderItemIdDoesNotExistException;
}
