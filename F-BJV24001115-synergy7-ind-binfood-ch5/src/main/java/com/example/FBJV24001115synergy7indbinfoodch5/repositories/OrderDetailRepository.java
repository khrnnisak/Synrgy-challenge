package com.example.FBJV24001115synergy7indbinfoodch5.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FBJV24001115synergy7indbinfoodch5.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch5.models.OrderDetail;
import com.example.FBJV24001115synergy7indbinfoodch5.models.Product;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    OrderDetail findByOrderId(UUID id);
    OrderDetail findByOrderAndProduct(Order order, Product product);
    List<OrderDetail> findByOrder(Order order);

}
