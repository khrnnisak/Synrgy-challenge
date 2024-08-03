package com.example.FBJV24001115synergy7indbinfoodch6.mapper;

import org.springframework.stereotype.Component;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.order.OrderDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.order.OrderResponse;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.orderDetail.OrderDetailDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.orderDetail.OrderDetailFieldDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.orderDetail.OrderDetailReportDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.product.ProductResponse;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch6.models.OrderDetail;

@Component
public class OrderMapper {
    public OrderDTO toOrderDTO(Order order){
        return OrderDTO.builder()
            .id(order.getId())
            .userId(order.getUser().getId())
            .destination(order.getDestination())
            .orderTime(order.getOrderTime())
            .status(order.getStatus())
            .build();
    }

    public OrderResponse toOrderResponse(Order order){
        return OrderResponse.builder()
            .userId(order.getUser().getId())
            .destination(order.getDestination())
            .build();
    }

    public OrderDetailDTO tOrderDetailDTO(OrderDetail orderDetail, OrderResponse order, ProductResponse product){
        return OrderDetailDTO.builder()
            .id(orderDetail.getId())
            .order(order)
            .product(product)
            .quantity(orderDetail.getQuantity())
            .totalPrice(orderDetail.getTotalPrice())
            .build();
    }

    public OrderDetailFieldDTO toField(OrderDetail orderDetail){
        return OrderDetailFieldDTO.builder()
            .productName(orderDetail.getProduct().getName())
            .quantity(orderDetail.getQuantity())
            .totalPrice(orderDetail.getTotalPrice())
            .merchant(orderDetail.getProduct().getMerchant().getName())
            .build();
    }
}
