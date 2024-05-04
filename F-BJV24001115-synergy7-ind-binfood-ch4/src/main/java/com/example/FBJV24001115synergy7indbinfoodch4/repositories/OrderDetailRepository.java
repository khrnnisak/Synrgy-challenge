package com.example.FBJV24001115synergy7indbinfoodch4.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FBJV24001115synergy7indbinfoodch4.models.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    OrderDetail findByOrderId(UUID id);

}
