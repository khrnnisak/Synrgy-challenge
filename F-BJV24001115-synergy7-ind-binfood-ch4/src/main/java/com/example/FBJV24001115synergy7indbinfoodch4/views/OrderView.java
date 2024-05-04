package com.example.FBJV24001115synergy7indbinfoodch4.views;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.FBJV24001115synergy7indbinfoodch4.controllers.OrderController;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.AdditionalUtil;

@Component
public class OrderView {
    @Autowired OrderController orderController;
    Scanner input = new Scanner(System.in);
    public void displayMainMenu(){
        displayHeader();
        displayOrderMenu();
    }
    public void displayHeader(){
        System.out.println(AdditionalUtil.headerFormat("Selamat Datang di Halaman Order"));
    }
    public void displayOrderMenu(){
        System.out.println("Pilih halaman : ");
        System.out.println("1. Tambah Order");
        System.out.println("2. Ubah Order");
        System.out.println("3. Hapus Order");
        System.out.println("0. Kembali ke main menu");
        System.out.print("Pilihan anda : ");
        int choice = input.nextInt();
        orderController.orderMenuSelection(choice);
    }
    public void createView(){

    }

    public void updateView(){
        
    }

    public void deleteView(){
        
    }

    public void displayOrders(){
        
    }
}
