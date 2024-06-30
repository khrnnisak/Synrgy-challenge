 package com.example.FBJV24001115synergy7indbinfoodch6.services;

import java.util.List;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch6.dto.order.OrderUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.orderDetail.OrderDetailDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.orderDetail.OrderDetailUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch6.models.OrderDetail;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Product;

public interface OrderDetailService {
    List<OrderDetail> getOrderDetail();

    String getListToString(Order order);
    double getTotalPrice(Product product, int quantity);
    int getTotalqty(Order order);
    String getreceipt(String payment, Order order);


    OrderDetailDTO create(OrderDetail orderDetail);
    void delete(UUID id);
    OrderDetailDTO update(UUID id, OrderDetailUpdateDTO orderDetailUpdateDTO);
    double getTotal(Order order);
    OrderDetailDTO getOrderDetailById(UUID id);
    List<OrderDetail> getOrderDetailByOrder(Order order);

}
