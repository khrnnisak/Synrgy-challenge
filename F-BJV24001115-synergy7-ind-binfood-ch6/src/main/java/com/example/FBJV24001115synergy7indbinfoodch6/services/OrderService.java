package com.example.FBJV24001115synergy7indbinfoodch6.services;

import java.util.List;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch6.dto.order.OrderDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.order.OrderUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Order;

public interface OrderService {
    OrderDTO createOrder(Order order);
    OrderDTO updateOrder(UUID id, OrderUpdateDTO orderUpdateDTO);
    void deleteOrder(UUID id);
    List<Order> getAllOrder();
    OrderDTO getById(UUID id);

}
