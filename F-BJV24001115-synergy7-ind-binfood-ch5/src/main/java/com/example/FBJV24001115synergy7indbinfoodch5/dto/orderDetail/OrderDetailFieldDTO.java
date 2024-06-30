package com.example.FBJV24001115synergy7indbinfoodch5.dto.orderDetail;

import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch5.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch5.models.Product;

import lombok.Data;

@Data
public class OrderDetailFieldDTO {
    private String productName;
    private Integer quantity;
    private Double total_price;
}
