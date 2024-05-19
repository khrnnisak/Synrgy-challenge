package com.example.FBJV24001115synergy7indbinfoodch5.dto.order;

import java.time.LocalDate;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch5.models.User;

import lombok.Data;

@Data
public class OrderDTO {
    private UUID id;
    private LocalDate order_time;
    private String destination;
    private Boolean isCompleted;
    private User user;
}
