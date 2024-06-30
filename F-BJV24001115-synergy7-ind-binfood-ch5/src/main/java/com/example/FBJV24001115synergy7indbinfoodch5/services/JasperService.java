package com.example.FBJV24001115synergy7indbinfoodch5.services;

import java.util.List;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch5.dto.orderDetail.OrderDetailReportDTO;
import net.sf.jasperreports.engine.JRException;


public interface JasperService {
    byte[] getOrderInvoice(OrderDetailReportDTO orderDetailReportDTO) throws JRException;

}
