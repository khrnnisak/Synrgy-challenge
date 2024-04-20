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
    long getOrderByName(String name);


    String create(Order order);
    String delete(Long id);
    String update(Long id, int quantity);


}
