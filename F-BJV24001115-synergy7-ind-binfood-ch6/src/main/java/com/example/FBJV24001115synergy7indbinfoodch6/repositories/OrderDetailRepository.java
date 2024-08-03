package com.example.FBJV24001115synergy7indbinfoodch6.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.FBJV24001115synergy7indbinfoodch6.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch6.models.OrderDetail;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Product;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    OrderDetail findByOrderId(UUID id);
    
    OrderDetail findByOrderAndProduct(Order order, Product product);
    List<OrderDetail> findByOrder(Order order);

    @Query(value = "select od.* from order_detail od \r\n" + //
                "left join orders o on o.id = od.order_id\r\n" + //
                "where o.user_id = ? and o.status != 'BATAL' and od.deleted_date is null", nativeQuery = true)
    List<OrderDetail> findByUserId(UUID userId);


}
