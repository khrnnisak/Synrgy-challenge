 package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch4.models.OrderDetail;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;

public interface OrderDetailService {
    List<OrderDetail> getOrderDetail();

    String getListToString(Order order);
    double getTotalPrice(Product product, int quantity);
    int getTotalqty(Order order);
    String getreceipt(String payment, Order order);
    // UUID getOrderDetailId(String name);


    void create(OrderDetail orderDetail);
    void delete(UUID id);
    void update(UUID id, int quantity);
    double getTotal(Order order);

    OrderDetail getProductByOrderAndProduct(Order order, Product product);
    List<OrderDetail> getOrderDetailByOrder(Order order);

}
