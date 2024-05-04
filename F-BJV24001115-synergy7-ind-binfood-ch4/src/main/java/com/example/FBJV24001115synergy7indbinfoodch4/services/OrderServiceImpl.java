package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch4.models.User;
import com.example.FBJV24001115synergy7indbinfoodch4.repositories.OrderRepository;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.FormatMessageUtil;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Override
    public void createOrder(Order order) {
        try {
            Optional<Order> optionalOrder = Optional.ofNullable(order);
            if (optionalOrder.isPresent()) {
                System.out.println(FormatMessageUtil.duplicateMessage());
            }else{
                orderRepository.save(order);
                System.out.println(FormatMessageUtil.succesToAddMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToAddMessage() + e);
        } 
        
    }

    @Override
    public void updateOrder(UUID id, String destination) {
        try {
            Optional<Order> existOrder = orderRepository.findById(id);
            if (!existOrder.isPresent()) {
                    System.out.println(FormatMessageUtil.notFoundMessage());
            }else{
                Order choosenOrder = existOrder.get();
                choosenOrder.setDestination(destination);
                orderRepository.save(choosenOrder);
                System.out.println(FormatMessageUtil.succesToEditMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToAddMessage() + e);
        } 
    }

    @Override
    public void deleteOrder(UUID id) {
        try { 
            Optional<Order> existOrder = orderRepository.findById(id);
            if (!existOrder.isPresent()) {
                    System.out.println(FormatMessageUtil.notFoundMessage());
            }else{
                Order choosenOrder = existOrder.get();
                orderRepository.delete(choosenOrder);
                System.out.println(FormatMessageUtil.succesToDeleteMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToAddMessage() + e);
        }

    }

    @Override
    public UUID getOrderId(User user, String destination_address) {
        try { 
            Optional <Order> existOrder = Optional.ofNullable(orderRepository.findByUserAndDestination(user, destination_address));
            if (!existOrder.isPresent()) {
                System.out.println(FormatMessageUtil.notFoundMessage());
            }else{
                Order choosenOrder = existOrder.get();
                return choosenOrder.getId();
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToAddMessage() + e);
        }
        return null;
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

}
