package com.example.FBJV24001115synergy7indbinfoodch7.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FBJV24001115synergy7indbinfoodch7.dto.orderDetail.OrderDetailCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.orderDetail.OrderDetailDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.orderDetail.OrderDetailReportDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.orderDetail.OrderDetailUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch7.services.JasperService;
import com.example.FBJV24001115synergy7indbinfoodch7.services.OrderDetailService;
import com.example.FBJV24001115synergy7indbinfoodch7.services.OrderService;
import com.example.FBJV24001115synergy7indbinfoodch7.utils.AdditionalUtil;
import com.example.FBJV24001115synergy7indbinfoodch7.utils.FormatMessageUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("order/detail")
public class OrderDetailController {
    @Autowired OrderDetailService orderDetailService;
    @Autowired ProductController productController;
    @Autowired OrderController orderController;
    @Autowired ModelMapper modelMapper;
    @Autowired JasperService jasperService;
    @Autowired OrderService orderService;

    @GetMapping("{user_id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> showAllOrderDetail(@PathVariable("user_id") UUID user_id){
        Map<String, Object> response = new HashMap<>();

        List<OrderDetailDTO> orderDetails = orderDetailService.getOrderDetail(user_id);
        response.put("status", "success");
        if (orderDetails.isEmpty()) {
            response.put("data", null);
            response.put("message", "Merchant is empty");
        }else{
            response.put("data", orderDetails);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> createOrderDetail(@RequestBody OrderDetailCreateDTO orderDetailCreateDTO){
        OrderDetailDTO orderDetailDTO = orderDetailService.create(orderDetailCreateDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "suscces");
        response.put("data", orderDetailDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") UUID id, @RequestBody OrderDetailUpdateDTO orderDetailUpdateDTO){
        OrderDetailDTO orderDetailDTO = orderDetailService.update(id, orderDetailUpdateDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "suscces");
        response.put("data", orderDetailDTO);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id){
        orderDetailService.delete(id);
        return ResponseEntity.ok("Successfully deleted");
    }

    @GetMapping("checkout/{order_id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> checkout(@PathVariable("order_id") UUID orderId){

        OrderDetailReportDTO orderDetails = orderDetailService.getOrderDetailByOrder(orderId);
        if (orderDetails != null) {
            orderService.prosesOrder(orderId);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", orderDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    } 

    public void printReceipt(String payment, Order order) {
        String path = AdditionalUtil.pathFormat();

        File file = new File(path);
        try {
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(orderDetailService.getreceipt(payment, order));
                fileWriter.close();
                log.info(FormatMessageUtil.successMessageFormat("Nota Berhasil Dicetak"));
            } else {
                log.error(FormatMessageUtil.errorMessageFormat("File already exists"));
                
            }
        } catch (IOException e) {
            log.error(FormatMessageUtil.errorMessageFormat("Terjadi error"));
        }
    }



}
