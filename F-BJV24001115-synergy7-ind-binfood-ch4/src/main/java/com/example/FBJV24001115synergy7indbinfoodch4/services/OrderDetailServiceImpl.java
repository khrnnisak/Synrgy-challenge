package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch4.repositories.OrderRepository;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{

    @Autowired
    OrderRepository orderDetailRepository;

    @Override
    public List<Order> getOrderDetail() {
        return orderDetailRepository.findAll();
    }

    @Override
    public String getListToString() {
        return null;
    }


    @Override
    public int getTotalPrice() {
        return 0;
    }

    @Override
    public int getTotalqty() {
        return 0;
    }

    @Override
    public String getreceipt(String payment) {
        return null;
    }

    @Override
    public UUID getOrderDetailId(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void create(Order order) {
    }

    @Override
    public void delete(UUID id) {
    }

    @Override
    public void update(UUID id, int quantity) {
    }

}
