package com.example.FBJV24001115synergy7indbinfoodch6.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.FBJV24001115synergy7indbinfoodch6.dto.order.OrderCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.order.OrderDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.order.OrderUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.user.UserDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch6.models.accounts.User;
import com.example.FBJV24001115synergy7indbinfoodch6.services.OrderService;

import org.modelmapper.ModelMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {

    @Autowired OrderService orderService;
    @Autowired UserController userController;
    @Autowired ModelMapper modelMapper;
    
    @GetMapping()
    public ResponseEntity<List<OrderDTO>>  showAllOrders(){
        List<Order> orders = orderService.getAllOrder();
        List<OrderDTO> orderList = orders
                .stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .toList();
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> createdOrder(@RequestBody OrderCreateDTO orderCreateDTO){
        ResponseEntity<UserDTO> userResponse = userController.getUserById(orderCreateDTO.getUser_id());
        UserDTO userDTO = userResponse.getBody();
        User user = modelMapper.map(userDTO, User.class);   
        Order order = Order.builder()
            .destination(orderCreateDTO.getDestination().toLowerCase())
            .user(user)
            .order_time(LocalDate.now())
            .build();
        OrderDTO orderDTO = orderService.createOrder(order);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "suscces");
        response.put("data", orderDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>>  updateOrder(@PathVariable("id") UUID id, @RequestBody OrderUpdateDTO orderUpdateDTO){

        Map<String, Object> response = new HashMap<>();

        OrderDTO orderDTO = orderService.updateOrder(id, orderUpdateDTO);
        response.put("status", "suscces");
        response.put("data", orderDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") UUID id){
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("by-id/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") UUID id){
        OrderDTO order =  orderService.getById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }

}
