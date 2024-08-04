package com.example.FBJV24001115synergy7indbinfoodch7.dto.order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private UUID userId;
    private String destination;
}
