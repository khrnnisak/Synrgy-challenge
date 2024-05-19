package com.example.FBJV24001115synergy7indbinfoodch5.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FBJV24001115synergy7indbinfoodch5.dto.order.OrderDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.dto.orderDetail.OrderDetailCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.dto.orderDetail.OrderDetailDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.dto.orderDetail.OrderDetailUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.dto.product.ProductDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch5.models.OrderDetail;
import com.example.FBJV24001115synergy7indbinfoodch5.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch5.services.OrderDetailService;
import com.example.FBJV24001115synergy7indbinfoodch5.utils.AdditionalUtil;
import com.example.FBJV24001115synergy7indbinfoodch5.utils.FormatMessageUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("order/detail")
public class OrderDetailController {
    @Autowired OrderDetailService orderDetailService;
    @Autowired ProductController productController;
    @Autowired OrderController orderController;
    @Autowired ModelMapper modelMapper;

    @GetMapping("{order_id}")
    public ResponseEntity<List<OrderDetailDTO>> showAllOrderDetail(@PathVariable("order_id") UUID order_id){
        ResponseEntity<OrderDTO> order = orderController.getOrderById(order_id);
        OrderDTO orderDTO = order.getBody();
        Order choosenOrder = modelMapper.map(orderDTO, Order.class);
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailByOrder(choosenOrder);
        List<OrderDetailDTO> orderDetailList = orderDetails
                .stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailDTO.class))
                .toList();
        return new ResponseEntity<>(orderDetailList, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Map<String, Object>> createOrderDetail(@RequestBody OrderDetailCreateDTO orderDetailCreateDTO){
        ResponseEntity<ProductDTO> product = productController.geProductById(orderDetailCreateDTO.getProduct_id());
        ProductDTO productDTO = product.getBody();
        Product choosenProduct = modelMapper.map(productDTO, Product.class);
        ResponseEntity<OrderDTO> order = orderController.getOrderById(orderDetailCreateDTO.getOrder_id());

        OrderDTO orderDTO = order.getBody();
        Order choosenOrder = modelMapper.map(orderDTO, Order.class);
        double total_price = orderDetailService.getTotalPrice(choosenProduct, orderDetailCreateDTO.getQuantity());
        OrderDetail orderDetail = OrderDetail.builder()
                .order(choosenOrder)
                .product(choosenProduct)
                .quantity(orderDetailCreateDTO.getQuantity())
                .total_price(total_price)
                .build();
        OrderDetailDTO orderDetailDTO = orderDetailService.create(orderDetail);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "suscces");
        response.put("data", orderDetailDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") UUID id, @RequestBody OrderDetailUpdateDTO orderDetailUpdateDTO){
        OrderDetailDTO choosenOrderDetail = orderDetailService.getOrderDetailById(id);
        double total_price = orderDetailService.getTotalPrice(choosenOrderDetail.getProduct(), orderDetailUpdateDTO.getQuantity());
        orderDetailUpdateDTO.setTotal_price(total_price);
        OrderDetailDTO orderDetailDTO = orderDetailService.update(id, orderDetailUpdateDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "suscces");
        response.put("data", orderDetailDTO);
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

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id){
        orderDetailService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
