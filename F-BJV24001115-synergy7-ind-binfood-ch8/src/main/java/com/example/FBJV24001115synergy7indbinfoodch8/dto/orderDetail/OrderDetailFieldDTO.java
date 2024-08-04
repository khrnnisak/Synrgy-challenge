package com.example.FBJV24001115synergy7indbinfoodch8.dto.orderDetail;

import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch8.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch8.models.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailFieldDTO {
    private String productName;
    private Integer quantity;
    private Double totalPrice;
    private String merchant;
}
