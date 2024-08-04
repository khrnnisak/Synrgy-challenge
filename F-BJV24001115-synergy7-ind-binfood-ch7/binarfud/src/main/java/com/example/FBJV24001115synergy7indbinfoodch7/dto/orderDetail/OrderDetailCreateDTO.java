package com.example.FBJV24001115synergy7indbinfoodch7.dto.orderDetail;

import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch7.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch7.models.Product;

import lombok.Data;

@Data
public class OrderDetailCreateDTO {
    private Integer quantity;
    private UUID orderId;
    private UUID productId;
}
