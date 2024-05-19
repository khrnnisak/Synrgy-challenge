package com.example.FBJV24001115synergy7indbinfoodch5.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import com.example.FBJV24001115synergy7indbinfoodch5.dto.order.OrderDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.dto.order.OrderUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch5.repositories.OrderRepository;
import com.example.FBJV24001115synergy7indbinfoodch5.utils.FormatMessageUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class OrderServiceImpl implements OrderService{

    @Autowired OrderRepository orderRepository;
    @Autowired ModelMapper modelMapper;

    @Override
    public OrderDTO createOrder(Order order) {
        try {
            Optional<Order> optionalOrder = Optional.ofNullable(order);
            if (!optionalOrder.isPresent()) {
                log.error(FormatMessageUtil.errorMessageFormat("Must be not null"));
            }
            orderRepository.save(order);
            log.info(FormatMessageUtil.succesToAddMessage());
            return modelMapper.map(order, OrderDTO.class);
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToAddMessage());
        } 
        return null;
    }

    @Override
    public OrderDTO updateOrder(UUID id, OrderUpdateDTO orderUpdateDTO) {
        try {
            Optional<Order> existOrder = orderRepository.findById(id);
            if (!existOrder.isPresent()) {
                    log.error(FormatMessageUtil.notFoundMessage());
            }else{
                Order choosenOrder = existOrder.get();
                choosenOrder.setDestination(orderUpdateDTO.getNewDestination());
                orderRepository.save(choosenOrder);
                System.out.println(FormatMessageUtil.succesToEditMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToEditMessage());
        } 
        return null;
    }

    @Override
    public void deleteOrder(UUID id) {
        try { 
            Optional<Order> existOrder = orderRepository.findById(id);
            if (!existOrder.isPresent()) {
                    log.error(FormatMessageUtil.notFoundMessage());
            }else{
                Order choosenOrder = existOrder.get();
                orderRepository.delete(choosenOrder);
                log.info(FormatMessageUtil.succesToDeleteMessage());
            }
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToDeleteMessage());
        }

    }

    @Override
    public OrderDTO getById(UUID id) {
        try {
            Optional<UUID> optional = Optional.ofNullable(id);
            if (!optional.isPresent()) {
                System.out.println(FormatMessageUtil.errorMessageFormat("Masukan tidak boleh kosong"));
                
            }
            Optional<Order> existOrder = orderRepository.findById(id);
            if (!existOrder.isPresent()) {
                log.error(FormatMessageUtil.notFoundMessage());
            }else{
                Order order = existOrder.get();
                return modelMapper.map(order, OrderDTO.class);

            }
        } catch (Exception e) {
            log.error(FormatMessageUtil.notFoundMessage());
        }
        return null;
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

}
