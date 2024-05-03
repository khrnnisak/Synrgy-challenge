package com.example.FBJV24001115synergy7indbinfoodch4.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch4.models.User;
import com.example.FBJV24001115synergy7indbinfoodch4.services.OrderService;

public class OrderController {

    @Autowired
    OrderService orderService;

    public void showAllOrders(){
        List<Order> orders = orderService.getAllOrder();
    }

    public void createdOrder(Order order){
        orderService.createOrder(order);
    }

    public void updateOrder(UUID id, String destination){
        showAllOrders();
        orderService.updateOrder(id, destination);
        showAllOrders();

    }

    public void deleteOrder(UUID id){
        showAllOrders();
        orderService.deleteOrder(id);
        showAllOrders();
    }

    public UUID getOrderId(User user, String destination){
        return orderService.getOrderId(user, destination);
        
    }
}
