package com.example.FBJV24001115synergy7indbinfoodch7.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.example.FBJV24001115synergy7indbinfoodch7.dto.orderDetail.OrderDetailReportDTO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;


@Service
public class JasperServiceImpl implements JasperService{

@Override
public byte[] getOrderInvoice(OrderDetailReportDTO orderDetailReportDTO) throws JRException {
    JasperReport jasperReport;
        try {
            jasperReport = (JasperReport) JRLoader
                    .loadObject(ResourceUtils.getFile("movie-list-report.jasper"));
        } catch (JRException | FileNotFoundException e) {
            try {
                File file = ResourceUtils.getFile("classpath:jasper/movie-list-report.jrxml");
                jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
                JRSaver.saveObject(jasperReport, "movie-list-report.jasper");
            } catch (FileNotFoundException | JRException ex) {
                throw new RuntimeException(ex);
            }
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderDetailReportDTO.getOrders());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user", String.valueOf(orderDetailReportDTO.getUser()));
        parameters.put("destination", String.valueOf(orderDetailReportDTO.getDestination()));
        parameters.put("total", String.valueOf(orderDetailReportDTO.getTotal()));
        parameters.put("totalQty", String.valueOf(orderDetailReportDTO.getTotalQty()));
        parameters.put("payment", String.valueOf(orderDetailReportDTO.getPayment()));
        parameters.put("date", String.valueOf(orderDetailReportDTO.getDate()));

        JasperPrint jasperPrint;
        byte[] reportContent;
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            reportContent = JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            e.printStackTrace();
            throw  new RuntimeException();
        }

        return reportContent;
    }
}
