package com.example.FBJV24001115synergy7indbinfoodch4.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch4.models.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID>{
    Order findByUserAndDestination(User user, String destination);

}
