package com.example.FBJV24001115synergy7indbinfoodch4.views;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.FBJV24001115synergy7indbinfoodch4.controllers.HomeController;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.AdditionalUtil;

@Component
public class MainView {

    @Autowired 
    HomeController homeController;
    Scanner input = new Scanner(System.in);

    public void displayView(){
        displayHeader();
        displayPageMenu();
    }
    public void displayHeader(){
        System.out.println(AdditionalUtil.headerFormat("Selamat Datang di BinarFud"));
    }

    public void displayPageMenu(){
        System.out.println("Pilih halaman : ");
        System.out.println("1. User");
        System.out.println("2. Product");
        System.out.println("3. Merchant");
        System.out.println("4. Order");
        System.out.println("0. Keluar Aplikasi");
        System.out.print("Pilihan anda : ");
        int choice = input.nextInt();
        homeController.pageMenu(choice);
        
    }
}
