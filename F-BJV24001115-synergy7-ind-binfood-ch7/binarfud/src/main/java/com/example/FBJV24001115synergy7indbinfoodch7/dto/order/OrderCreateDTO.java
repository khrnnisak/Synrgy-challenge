package com.example.FBJV24001115synergy7indbinfoodch7.dto.order;

import java.util.UUID;

import lombok.Data;

@Data
public class OrderCreateDTO {
    private UUID userId;
    private String destination;
}
