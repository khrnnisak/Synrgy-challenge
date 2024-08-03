package com.example.FBJV24001115synergy7indbinfoodch6.dto.order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch6.models.Order.OrderStatus;
import com.example.FBJV24001115synergy7indbinfoodch6.models.accounts.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDTO {
    private UUID id;
    private LocalDateTime orderTime;
    private String destination;
    private OrderStatus status;
    private UUID userId;
}
