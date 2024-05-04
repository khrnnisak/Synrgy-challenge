package com.example.FBJV24001115synergy7indbinfoodch4.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch4.models.User;
import com.example.FBJV24001115synergy7indbinfoodch4.services.OrderService;
import com.example.FBJV24001115synergy7indbinfoodch4.views.MainView;
import com.example.FBJV24001115synergy7indbinfoodch4.views.OrderView;

@Controller

public class OrderController {

    @Autowired OrderService orderService;
    @Autowired OrderView orderView;
    @Autowired MainView mainView;

    public List<Order> showAllOrders(){
        return orderService.getAllOrder();
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

    public void orderMenuSelection(int choice){
        switch (choice) {
            case 1:
                orderView.createView();
                break;
            case 2:
                orderView.updateView();
                break;
            case 3:
                orderView.deleteView();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                mainView.displayView();
                break;
        }
    }
}
