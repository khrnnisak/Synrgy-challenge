package org.example.Services;

import java.util.List;

import org.example.Models.Order;

public interface OrderService {
    List<Order> getOrderList();

    String getListToString();
    int getTotalPrice();
    int getTotalqty();
    String getreceipt(String payment);
    Order getOrderById(Long id);
    void getOrderByName(String name);


    void create(Order order);
    void delete(Long id);
    void update(Long id, Order order);


}
