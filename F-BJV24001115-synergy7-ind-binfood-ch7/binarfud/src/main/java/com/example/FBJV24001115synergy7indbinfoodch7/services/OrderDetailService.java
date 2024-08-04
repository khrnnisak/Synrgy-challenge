 package com.example.FBJV24001115synergy7indbinfoodch7.services;

import java.util.List;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch7.dto.orderDetail.OrderDetailCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.orderDetail.OrderDetailDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.orderDetail.OrderDetailReportDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.orderDetail.OrderDetailUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch7.models.OrderDetail;
import com.example.FBJV24001115synergy7indbinfoodch7.models.Product;

public interface OrderDetailService {
    List<OrderDetailDTO> getOrderDetail(UUID userId);

    String getListToString(Order order);
    double getTotalPrice(Product product, int quantity);
    int getTotalqty(Order order);
    String getreceipt(String payment, Order order);


    OrderDetailDTO create(OrderDetailCreateDTO orderDetailCreateDTO);
    void delete(UUID id);
    OrderDetailDTO update(UUID id, OrderDetailUpdateDTO orderDetailUpdateDTO);
    double getTotal(Order order);
    OrderDetailDTO getOrderDetailById(UUID id);
    OrderDetailReportDTO getOrderDetailByOrder(UUID orderId);


}
