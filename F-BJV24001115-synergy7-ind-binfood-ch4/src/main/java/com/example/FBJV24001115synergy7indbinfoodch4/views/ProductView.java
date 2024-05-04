package com.example.FBJV24001115synergy7indbinfoodch4.views;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.FBJV24001115synergy7indbinfoodch4.controllers.ProductController;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.AdditionalUtil;

@Component
public class ProductView {
    @Autowired ProductController productController;

    Scanner input = new Scanner(System.in);
    public void displayMainMenu(){
        displayHeader();
        displayOrderMenu();
    }
    public void displayHeader(){
        System.out.println(AdditionalUtil.headerFormat("Selamat Datang di Halaman Product"));
    }
    public void displayOrderMenu(){
        System.out.println("Pilih halaman : ");
        System.out.println("1. Tambah Product");
        System.out.println("2. Perbarui Harga Product");
        System.out.println("3. Hapus Product");
        System.out.println("4. Lihat Product di merchant tertentu");
        System.out.println("0. Kembali ke main menu");
        System.out.print("Pilihan anda : ");
        int choice = input.nextInt();
        productController.productMenuSelection(choice);
    }
    public void createView(){

    }

    public void updateView(){
        
    }

    public void deleteView(){
        
    }

    public void displayProducts(){
        
    }

    public void displayProductMerchant(){
        
    }

}