 package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Order;

public interface OrderDetailService {
    List<Order> getOrderDetail();

    String getListToString();
    int getTotalPrice();
    int getTotalqty();
    String getreceipt(String payment);
    UUID getOrderDetailId(String name);


    void create(Order order);
    void delete(UUID id);
    void update(UUID id, int quantity);

}
