package com.example.FBJV24001115synergy7indbinfoodch6.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.FBJV24001115synergy7indbinfoodch6.dto.orderDetail.OrderDetailCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.orderDetail.OrderDetailDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.orderDetail.OrderDetailFieldDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.orderDetail.OrderDetailReportDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.orderDetail.OrderDetailUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.mapper.OrderMapper;
import com.example.FBJV24001115synergy7indbinfoodch6.mapper.ProductMapper;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch6.models.OrderDetail;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch6.repositories.OrderDetailRepository;
import com.example.FBJV24001115synergy7indbinfoodch6.repositories.OrderRepository;
import com.example.FBJV24001115synergy7indbinfoodch6.repositories.ProductRepository;
import com.example.FBJV24001115synergy7indbinfoodch6.utils.AdditionalUtil;
import com.example.FBJV24001115synergy7indbinfoodch6.utils.FormatMessageUtil;

import org.modelmapper.ModelMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class OrderDetailServiceImpl implements OrderDetailService{

    @Autowired OrderDetailRepository orderDetailRepository;
    @Autowired ModelMapper modelMapper;
    @Autowired OrderRepository orderRepository;
    @Autowired ProductRepository productRepository;
    @Autowired OrderMapper orderMapper;
    @Autowired ProductMapper productMapper;


    @Override
    public String getListToString(Order order) {
        StringBuilder receipt = new StringBuilder();
        for (OrderDetail orderDetail : orderDetailRepository.findByOrder(order)) {
            OrderDetail oDetail = orderDetailRepository.findByOrderId(orderDetail.getId());
            Product item = oDetail.getProduct();
            double subTotal = orderDetail.getTotalPrice();
            receipt.append(item.getName())
                    .append("\t\t")
                    .append(orderDetail.getQuantity())
                    .append("\t\t")
                    .append(AdditionalUtil.priceFormat((int) subTotal))
                    .append("\n");
        }
        receipt.append("-".repeat(20));
        receipt.append("\n");
        receipt.append("Total\t\t\t")
                .append(getTotalqty(order))
                .append("\t\t")
                .append(AdditionalUtil.priceFormat((int) getTotal(order)));
        return receipt.toString();
    }


    @Override
    public double getTotalPrice(Product product, int quantity) {
        return product.getPrice() * quantity;
    }
    @Override
    public double getTotal(Order order) {
        return orderDetailRepository.findByOrder(order).stream()
        .mapToDouble(OrderDetail::getTotalPrice)
        .sum();
    }

    @Override
    public int getTotalqty(Order order) {
        return orderDetailRepository.findByOrder(order).stream()
            .mapToInt(OrderDetail::getQuantity)
            .sum();
    }

    @Override
    public String getreceipt(String payment, Order order) {
        StringBuilder receipt = new StringBuilder();
        receipt.append(AdditionalUtil.headerFormat("BinarFud"))
                .append("\nTerima kasih sudah memesan di BinarFud\n\nDi bawah ini adalah pesanan Anda\n")
                .append("Pemesan : "+order.getUser().getUsername())
                .append("\nDestinasi Pengiriman : "+order.getDestination() +"\n\n");
        receipt.append(getListToString(order)).append("\n\nPembayaran\t\t\t: ").append(payment)
                .append("\nTanggal Transaksi\t\t: ")
                .append(AdditionalUtil.getCurrentDate());
        receipt.append(AdditionalUtil.headerFormat("Simpan struk ini sebagai bukti pembayaran"));

        return receipt.toString();
    }

    @Override
    public List<OrderDetailDTO> getOrderDetail(UUID userId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByUserId(userId);
        List<OrderDetailDTO> orderDetailList = orderDetails
            .stream()
            .map(order -> orderMapper.tOrderDetailDTO(order, 
                orderMapper.toOrderResponse(order.getOrder()), 
                productMapper.toProductResponse(order.getProduct())))
            .collect(Collectors.toList());
        return orderDetailList;
    }

    @Override
    public OrderDetailDTO create(OrderDetailCreateDTO orderDetailCreateDTO) {
        try {
            Order order = orderRepository.findById(orderDetailCreateDTO.getOrderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not found"));
            
            Product product = productRepository.findById(orderDetailCreateDTO.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found"));

            

            Optional<OrderDetail> getOrder = Optional
                .ofNullable(orderDetailRepository
                    .findByOrderAndProduct(order, product));

            if (getOrder.isPresent()) {
                log.error(FormatMessageUtil.errorMessageFormat("Pesanan Sudah tersedia, ubah untuk memperbarui pesanan atau buat pesanan baru"));
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pesanan Sudah tersedia, ubah untuk memperbarui pesanan");
            } else {
                OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .product(product)
                    .quantity(orderDetailCreateDTO.getQuantity())
                    .totalPrice(product.getPrice() * orderDetailCreateDTO.getQuantity())
                    .build();
                orderDetailRepository.save(orderDetail);
                orderDetail.setId(orderDetail.getId());
                log.info(FormatMessageUtil.succesToAddMessage());
                return orderMapper.tOrderDetailDTO(orderDetail, orderMapper.toOrderResponse(order), productMapper.toProductResponse(product));
            }
        } 
        catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToEditMessage() + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        } 
    }

    @Override
    public void delete(UUID id) {
        try {
            OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order detail not found"));

            orderDetailRepository.delete(orderDetail);
            log.info(FormatMessageUtil.succesToDeleteMessage());
        
        } 
        catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToEditMessage() + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        } 
    }

    @Override
    public OrderDetailDTO update(UUID id, OrderDetailUpdateDTO orderDetailUpdateDTO) {
        try {
            OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order detail not found"));

            orderDetail.setQuantity(orderDetailUpdateDTO.getQuantity());
            orderDetail.setTotalPrice(orderDetail.getProduct().getPrice() * orderDetailUpdateDTO.getQuantity());
            orderDetailRepository.save(orderDetail);
            log.info(FormatMessageUtil.succesToEditMessage());
            return orderMapper.tOrderDetailDTO(orderDetail, orderMapper.toOrderResponse(orderDetail.getOrder()), productMapper.toProductResponse(orderDetail.getProduct()));
        } 
        catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToEditMessage() + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    @Override
    public OrderDetailReportDTO getOrderDetailByOrder(UUID orderId) {
        try {
            Order existOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not found"));
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(existOrder);
            List<OrderDetailFieldDTO> orderDetailList = orderDetails
                .stream()
                .map(order -> orderMapper.toField(order))
                .collect(Collectors.toList());

            return OrderDetailReportDTO.builder()
                .user(existOrder.getUser().getId())
                .destination(existOrder.getDestination())
                .date(LocalDateTime.now())
                .orders(orderDetailList)
                .totalQty(getTotalqty(existOrder))
                .total(getTotal(existOrder))
                .payment("Binarpay")
                .build();
        } catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToEditMessage() + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    @Override
    public OrderDetailDTO getOrderDetailById(UUID id) {
       try {
            OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order detail not found"));
            return orderMapper.tOrderDetailDTO(orderDetail, orderMapper.toOrderResponse(orderDetail.getOrder()), productMapper.toProductResponse(orderDetail.getProduct()));
        } 
        catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToEditMessage() + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        } 
    }

}
