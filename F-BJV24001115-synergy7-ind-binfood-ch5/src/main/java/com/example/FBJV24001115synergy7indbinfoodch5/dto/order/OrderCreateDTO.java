package com.example.FBJV24001115synergy7indbinfoodch5.dto.order;

import java.util.UUID;

import lombok.Data;

@Data
public class OrderCreateDTO {
    private UUID user_id;
    private String destination;
}
