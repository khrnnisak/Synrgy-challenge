 package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.UUID;


import com.example.FBJV24001115synergy7indbinfoodch4.models.OrderDetail;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;

public interface OrderDetailService {
    List<OrderDetail> getOrderDetail();

    String getListToString();
    double getTotalPrice(Product product, int quantity);
    int getTotalqty();
    String getreceipt(String payment);
    UUID getOrderDetailId(String name);


    void create(OrderDetail orderDetail);
    void delete(UUID id);
    void update(UUID id, int quantity);
    double getTotal();

}
