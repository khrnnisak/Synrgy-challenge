package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch4.models.User;

public interface OrderService {
    void createOrder(Order order);
    void updateOrder(UUID id, String destination);
    void deleteOrder(UUID id);
    UUID getOrderId(User user, String destination);
    List<Order> getAllOrder();
    Order getById(UUID id);

}
