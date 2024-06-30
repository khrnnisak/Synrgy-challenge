package com.example.FBJV24001115synergy7indbinfoodch5.dto.orderDetail;


import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class OrderDetailReportDTO {
    private String user;
    private String destination;
    private Double total;
    private LocalDate date;
    private String payment;
    private Integer total_qty;
    private List<OrderDetailDTO> orders;
}
