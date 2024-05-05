package com.example.FBJV24001115synergy7indbinfoodch4.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch4.models.User;
import com.example.FBJV24001115synergy7indbinfoodch4.services.OrderService;
import com.example.FBJV24001115synergy7indbinfoodch4.views.MainView;
import com.example.FBJV24001115synergy7indbinfoodch4.views.OrderDetailView;
import com.example.FBJV24001115synergy7indbinfoodch4.views.OrderView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j

public class OrderController {

    @Autowired OrderService orderService;
    @Autowired OrderView orderView;
    @Autowired MainView mainView;
    @Autowired UserController userController;
    @Autowired OrderDetailView orderDetailView;

    public List<Order> showAllOrders(){
        return orderService.getAllOrder();
    }

    public void createdOrder(String pemesan, String destinasi){
        
        UUID user_id = userController.getUserId(pemesan.toLowerCase());
        User user = userController.getUserById(user_id);

        Order order = Order.builder()
            .destination(destinasi.toLowerCase())
            .user(user)
            .isCompleted(false)
            .order_time(LocalDate.now())
            .build();
        
        orderService.createOrder(order);
        UUID order_id = orderService.getOrderId(order.getUser(), order.getDestination());
        Order thisOrder = orderService.getById(order_id);
        
        orderDetailView.orderDetailMenu(thisOrder);
    }

    public void updateOrder(String pemesan, String oldDestination, String newDestination){

        UUID user_id = userController.getUserId(pemesan);
        User user = userController.getUserById(user_id);

        UUID order_id = getOrderId(user, oldDestination);
        orderService.updateOrder(order_id, oldDestination);

        orderView.displayMainMenu();

    }

    public void deleteOrder(String pemesan, String destinasi){
        
        UUID user_id = userController.getUserId(pemesan);
        User user = userController.getUserById(user_id);
        UUID order_id = getOrderId(user, destinasi);
        orderService.deleteOrder(order_id);

        orderView.displayMainMenu();
    }

    public UUID getOrderId(User user, String destination){
        return orderService.getOrderId(user, destination);
        
    }
    public Order getOrderById(UUID id){
        return orderService.getById(id);
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
            case 0:
                mainView.displayView();
                break;
            default:
                log.error("Pilihan tidak valid.");
                orderView.displayMainMenu();
                break;
        }
    }
}
