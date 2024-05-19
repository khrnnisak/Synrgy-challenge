package com.example.FBJV24001115synergy7indbinfoodch5.dto.orderDetail;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class OrderDetailUpdateDTO {
    private Integer quantity;

    @JsonIgnore
    private Double total_price;

}
