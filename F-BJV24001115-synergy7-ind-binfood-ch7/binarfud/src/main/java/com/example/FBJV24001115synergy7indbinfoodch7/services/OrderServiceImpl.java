package com.example.FBJV24001115synergy7indbinfoodch7.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.FBJV24001115synergy7indbinfoodch7.dto.order.OrderCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.order.OrderDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.mapper.OrderMapper;
import com.example.FBJV24001115synergy7indbinfoodch7.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch7.models.OrderDetail;
import com.example.FBJV24001115synergy7indbinfoodch7.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch7.models.Order.OrderStatus;
import com.example.FBJV24001115synergy7indbinfoodch7.models.accounts.User;
import com.example.FBJV24001115synergy7indbinfoodch7.repositories.OrderRepository;
import com.example.FBJV24001115synergy7indbinfoodch7.repositories.UserRepository;
import com.example.FBJV24001115synergy7indbinfoodch7.utils.FormatMessageUtil;

import org.modelmapper.ModelMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class OrderServiceImpl implements OrderService{

    @Autowired OrderRepository orderRepository;
    @Autowired ModelMapper modelMapper;
    @Autowired UserRepository userRepository;
    @Autowired OrderMapper orderMapper;
    @Autowired ProductService productService;

    @Override
    public OrderDTO createOrder(OrderCreateDTO orderCreateDTO) {
        try {
            User user = userRepository.findById(orderCreateDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
            Order order = Order.builder()
                .user(user)
                .destination(orderCreateDTO.getDestination())
                .orderTime(LocalDateTime.now())
                .status(OrderStatus.valueOf("BELUM"))
                .build();
            orderRepository.save(order);
            order.setId(order.getId());
            log.info(FormatMessageUtil.succesToAddMessage());
            return orderMapper.toOrderDTO(order);
        }catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        } 
    }

    @Override
    public OrderDTO updateOrder(UUID id, String destination) {
        try {
            Order existOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not found"));
            existOrder.setDestination(destination);
            orderRepository.save(existOrder);
            log.info(FormatMessageUtil.succesToEditMessage());
            return orderMapper.toOrderDTO(existOrder);
        } catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    @Override
    public void deleteOrder(UUID id) {
        try { 
            Order existOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not found"));
            orderRepository.delete(existOrder);
            log.info(FormatMessageUtil.succesToDeleteMessage());
        } catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }

    }

    @Override
    public OrderDTO getById(UUID id) {
        try {
            Order existOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not found"));
            return orderMapper.toOrderDTO(existOrder);
        } catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    @Override
    public List<OrderDTO> getAllOrder(UUID userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderDTO> orderList = orders
            .stream()
            .map(order -> orderMapper.toOrderDTO(order))
            .collect(Collectors.toList());
        return orderList;
    }

    @Override
    public void cancelOrder(UUID orderId) {
        try {
            Order existOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not found"));
            if (!existOrder.getStatus().equals(OrderStatus.PROSES)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not found");
            }
            existOrder.setStatus(OrderStatus.valueOf("BATAL"));
            orderRepository.save(existOrder);
            List<OrderDetail> orderDetails = existOrder.getOrderDetails();
            productService.updateStock(orderDetails, "cancel");
        } catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    @Override
    public void confirmOrder(UUID orderId) {
        try {
            Order existOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not found"));
            if (!existOrder.getStatus().equals(OrderStatus.PROSES)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not found");
            }
            existOrder.setStatus(OrderStatus.valueOf("SELESAI"));
            orderRepository.save(existOrder);
        } catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    @Override
    public void prosesOrder(UUID orderId) {
        try {
            Order existOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not found"));
            if (!existOrder.getStatus().equals(OrderStatus.BELUM)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not found");
            }
            existOrder.setStatus(OrderStatus.valueOf("PROSES"));
            orderRepository.save(existOrder);
            List<OrderDetail> orderDetails = existOrder.getOrderDetails();
            productService.updateStock(orderDetails, "proses");
        } catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

}
