package com.example.FBJV24001115synergy7indbinfoodch6.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.FBJV24001115synergy7indbinfoodch6.dto.order.OrderCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.order.OrderDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.services.OrderService;

import org.modelmapper.ModelMapper;


@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired OrderService orderService;
    @Autowired UserController userController;
    @Autowired ModelMapper modelMapper;
    
    @GetMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> showAllOrders(@RequestParam UUID userId){
        Map<String, Object> response = new HashMap<>();

        List<OrderDTO> orders = orderService.getAllOrder(userId);
        response.put("status", "success");
        if (orders.isEmpty()) {
            response.put("data", null);
            response.put("message", "Order is empty");
        }else{
            response.put("data", orders);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> createdOrder(@RequestBody OrderCreateDTO orderCreateDTO){
        Map<String, Object> response = new HashMap<>();
        
        OrderDTO orderDTO = orderService.createOrder(orderCreateDTO);
        response.put("status", "suscces");
        response.put("data", orderDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>>  updateOrder(@PathVariable("id") UUID id, @RequestParam String newDestination){

        Map<String, Object> response = new HashMap<>();

        OrderDTO orderDTO = orderService.updateOrder(id, newDestination);
        response.put("status", "suscces");
        response.put("data", orderDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") UUID id){
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Successfully deleted");

    }

    @GetMapping("by-id/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") UUID id){
        OrderDTO order =  orderService.getById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PatchMapping("cancel-order/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> cancelOrder(@PathVariable("id") UUID id){
        orderService.cancelOrder(id);
        return ResponseEntity.ok("Successfully canceled");

    }

    @PatchMapping("confirm-order/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> confirmOrder(@PathVariable("id") UUID id){
        orderService.confirmOrder(id);
        return ResponseEntity.ok("Successfully confirmed");

    }

    @PatchMapping("proses-order/{id}")
    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    public ResponseEntity<String> prosesOrder(@PathVariable("id") UUID id){
        orderService.prosesOrder(id);
        return ResponseEntity.ok("Successfully processed");
    }

}
