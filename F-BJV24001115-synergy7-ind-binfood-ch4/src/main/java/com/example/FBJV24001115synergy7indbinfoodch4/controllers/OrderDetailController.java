package com.example.FBJV24001115synergy7indbinfoodch4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch4.models.OrderDetail;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch4.models.OrderDetail.PaymentOrder;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product.CategoryProduct;
import com.example.FBJV24001115synergy7indbinfoodch4.services.OrderDetailService;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.FormatMessageUtil;
import com.example.FBJV24001115synergy7indbinfoodch4.views.MainView;
import com.example.FBJV24001115synergy7indbinfoodch4.views.OrderView;

@Controller
public class OrderDetailController {
    @Autowired OrderDetailService orderDetailService;
    @Autowired OrderView orderView;
    @Autowired MainView mainView;
    @Autowired ProductController productController;

    public void menuOrderDetailSelection(Order order, String choice, Product product){
        int lenght = productController.getCountProduct();
        if (choice.equalsIgnoreCase("99")) {
            if (lenght <= 0) {
                FormatMessageUtil.errorMessageFormat("Anda belum memilih menu");
                orderView.orderDetailMenu(order);
            }
        }else if(choice.equalsIgnoreCase("0")) {
            mainView.displayView();
        } else {
            int qty = orderView.askQty();
            String pymnt = orderView.getPayment();
            PaymentOrder payment = PaymentOrder.valueOf(pymnt.toUpperCase());
            double total_price = orderDetailService.getTotalPrice(product, qty);
            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .product(product)
                    .quantity(qty)
                    .payment(payment)
                    .total_price(total_price)
                    .build();
            orderDetailService.create(orderDetail);
        }
            
    }

}
