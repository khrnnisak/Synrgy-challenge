package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch4.models.User;
import com.example.FBJV24001115synergy7indbinfoodch4.repositories.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Override
    public void createOrder(Order order) {
        Optional<Order> optionalOrder = Optional.ofNullable(order);
        if (!optionalOrder.isPresent()) {
            throw new RuntimeException();
        }else{
            orderRepository.save(order);
        }
    }

    @Override
    public void updateOrder(UUID id, String destination) {
        Optional<Order> existOrder = orderRepository.findById(id);
        if (!existOrder.isPresent()) {
                throw new RuntimeException();
        }else{
            Order choosenOrder = existOrder.get();
            choosenOrder.setDestination_address(destination);
            orderRepository.save(choosenOrder);
        }
    }

    @Override
    public void deleteOrder(UUID id) {
        Optional<Order> existOrder = orderRepository.findById(id);
        if (!existOrder.isPresent()) {
                throw new RuntimeException();
        }else{
            Order choosenOrder = existOrder.get();
            orderRepository.delete(choosenOrder);
        }

    }

    @Override
    public UUID getOrderId(User user, String destination) {
        Optional <Order> existOrder = Optional.ofNullable(orderRepository.findByUserAndDestination(user, destination));
        if (!existOrder.isPresent()) {
            throw new RuntimeException();
        }else{
            Order choosenOrder = existOrder.get();
            return choosenOrder.getId();
        }
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

}
