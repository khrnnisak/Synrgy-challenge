package com.example.FBJV24001115synergy7indbinfoodch4.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch4.models.OrderDetail;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch4.services.OrderDetailService;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.AdditionalUtil;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.FormatMessageUtil;
import com.example.FBJV24001115synergy7indbinfoodch4.views.MainView;
import com.example.FBJV24001115synergy7indbinfoodch4.views.OrderDetailView;
import com.example.FBJV24001115synergy7indbinfoodch4.views.OrderView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j

public class OrderDetailController {
    @Autowired OrderDetailService orderDetailService;
    @Autowired OrderView orderView;
    @Autowired MainView mainView;
    @Autowired ProductController productController;
    @Autowired OrderDetailView orderDetailView;

    Scanner input = new Scanner(System.in);

    public void menuOrderDetailSelection(Order order, String choice){
        if (choice.equalsIgnoreCase("99")) {
            int lenght = productController.getCountProduct();
            if (lenght <= 0) {
                FormatMessageUtil.errorMessageFormat("Anda belum memilih menu");
                orderDetailView.orderDetailMenu(order);
            }else{
                orderDetailView.displayOrderDetailView(order);
            }
        }else if(choice.equalsIgnoreCase("0")) {
            orderView.displayMainMenu();
        } else {
            UUID product_id = productController.getProductId(choice);
            Product product = productController.geProductById(product_id);
            int qty = orderDetailView.askQty();
            double total_price = orderDetailService.getTotalPrice(product, qty);
            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .product(product)
                    .quantity(qty)
                    .total_price(total_price)
                    .build();
            orderDetailService.create(orderDetail);
            orderDetailView.orderDetailMenu(order);
        }   
    }

    public void orderDetailSelection(int choice, Order order){
        switch (choice) {
            case 1:
                int pymnt = orderDetailView.getPayment();
                String payment = "";
                if (pymnt == 1) {
                    payment = "Binar Cash";
                }else if(pymnt == 2){
                    payment = "Binar Pay";
                }else if(pymnt == 3){
                    payment = "Binar Pay Later";
                }else{
                    System.out.println(FormatMessageUtil.errorMessageFormat("Pilihan salah!"));
                }
                printReceipt(payment, order);
                System.exit(0);
                break;
            case 2:
                orderDetailView.updateOrderDetail(order);
                orderDetailView.displayOrderDetailView(order);
                break;
            case 3:
                orderDetailView.deleteOrderDetail(order);
                orderDetailView.displayOrderDetailView(order);
                break;
            default:
                log.error("Pilihan tidak valid.");
                orderDetailView.displayOrderDetailView(order);
                break;
        }
    }
    public void printReceipt(String payment, Order order) {
        String path = AdditionalUtil.pathFormat();

        File file = new File(path);
        try {
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(orderDetailService.getreceipt(payment, order));
                fileWriter.close();
                FormatMessageUtil.successMessageFormat("Nota Berhasil Dicetak");
            } else {
                log.error(FormatMessageUtil.errorMessageFormat("File already exists"));
                ;
            }
        } catch (IOException e) {
            FormatMessageUtil.errorMessageFormat("Terjadi error");
            throw new RuntimeException(e);
        }
    }

    public void update(String menu, int newQuantity, Order order){
        UUID uuid = productController.getProductId(menu);
        Product product = productController.geProductById(uuid);
        OrderDetail orderDetail = orderDetailService.getProductByOrderAndProduct(order, product);
        orderDetailService.update(orderDetail.getId(), newQuantity);
        orderDetailView.displayOrderDetailView(order);
    }

    public void delete(String menu, Order order){
        UUID uuid = productController.getProductId(menu);
        Product product = productController.geProductById(uuid);
        OrderDetail orderDetail = orderDetailService.getProductByOrderAndProduct(order, product);
        orderDetailService.delete(orderDetail.getId());
        orderDetailView.displayOrderDetailView(order);
    }

}
