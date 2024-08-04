package com.example.FBJV24001115synergy7indbinfoodch8.dto.orderDetail;



import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailReportDTO {
    private UUID user;
    private String destination;
    private Double total;
    private LocalDateTime date;
    private String payment;
    private Integer totalQty;
    private List<OrderDetailFieldDTO> orders;
}
