package com.example.FBJV24001115synergy7indbinfoodch7.services;

import java.util.List;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch7.dto.order.OrderCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.order.OrderDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.models.Order;

public interface OrderService {
    OrderDTO createOrder(OrderCreateDTO orderCreateDTO);
    OrderDTO updateOrder(UUID id, String destination);
    void deleteOrder(UUID id);
    List<OrderDTO> getAllOrder(UUID userId);
    OrderDTO getById(UUID id);

    void cancelOrder(UUID orderId);
    void confirmOrder(UUID orderId);
    void prosesOrder(UUID orderId);

}
