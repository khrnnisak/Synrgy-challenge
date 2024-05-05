package com.example.FBJV24001115synergy7indbinfoodch4.views;

import java.util.Scanner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.FBJV24001115synergy7indbinfoodch4.controllers.OrderDetailController;
import com.example.FBJV24001115synergy7indbinfoodch4.controllers.ProductController;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.AdditionalUtil;

@Component
public class OrderDetailView {
    
    @Autowired ProductController productController;
    @Autowired ProductView productView;
    @Autowired OrderDetailController orderDetailController;

    Scanner input = new Scanner(System.in);

    public void orderDetailMenu(Order order){
        System.out.println(AdditionalUtil.headerFormat("Selamat Datang di Menu Pesanan"));
        productView.displayProducts();
        System.out.println("99. Pesan dan bayar");
        System.out.println("0. Kembali");
        System.out.print(" Pilihan anda : ");
        input.nextLine();
        String choice = input.nextLine();
        orderDetailController.menuOrderDetailSelection(order, choice);
        
    }

    public int askQty(){
        System.out.print("Masukkan banyak pesanan : ");
        int quantity = input.nextInt();
        return quantity;
    }

    public int getPayment(){
        System.out.println("1. BinarCash");
        System.out.println("2. BinarPay");
        System.out.println("3. BinarPayLater");
        System.out.print("Pilih Payment : ");
        int choice = input.nextInt();
        return choice;
    }

    public void displayOrderDetailView(Order order){
        //display order detail by order
        System.out.println("1. Konfirmasi Pembayaran");
        System.out.println("2. Ubah Pesanan");
        System.out.println("3. Hapus Pesanan");
        System.out.println("0. Kembali");
        System.out.print("Pilihan anda : ");
        int choice = input.nextInt();
        orderDetailController.orderDetailSelection(choice, order);
    }

    public void updateOrderDetail(Order order){
        productView.displayProducts();
        System.out.print("Menu yang ingin anda ubah : ");
        input.nextLine();
        String menu = input.nextLine();
        System.out.print("Masukkan banyak pesanan yang baru : ");
        int newQuantity = input.nextInt();
        orderDetailController.update(menu, newQuantity, order);
       
        
    }

    public void deleteOrderDetail(Order order){
        
        productView.displayProducts();
        System.out.print("Menu yang ingin anda hapus : ");
        input.nextLine();
        String menu = input.nextLine();
        System.out.print("Apakah anda yakin? (Y/N) ");
        String confirm = input.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            orderDetailController.delete(menu, order);
        }
    }
}
