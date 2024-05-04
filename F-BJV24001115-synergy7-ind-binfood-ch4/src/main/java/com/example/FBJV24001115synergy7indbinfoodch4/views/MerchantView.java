package com.example.FBJV24001115synergy7indbinfoodch4.views;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.FBJV24001115synergy7indbinfoodch4.controllers.MerchantController;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.AdditionalUtil;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.FormatMessageUtil;

@Component
public class MerchantView {
    @Autowired MerchantController merchantController;
    Scanner input = new Scanner(System.in);
    public void displayMainMenu(){
        displayHeader();
        displayMerchantMenu();
    }
    public void displayHeader(){
        System.out.println(AdditionalUtil.headerFormat("Selamat Datang di Halaman Merchant"));
    }
    public void displayMerchantMenu(){
        displayAllMerchant();
        System.out.println("Pilih halaman : ");
        System.out.println("1. Tambah Merchant");
        System.out.println("2. Ubah Lokasi Merchant");
        System.out.println("3. Hapus Merchant");
        System.out.println("4. Buka/Tutup Merchant");
        System.out.println("0. Kembali ke main menu");
        System.out.print("Pilihan anda : ");
        int choice = input.nextInt();
        merchantController.merchantMenuSelection(choice);
    }
    public void displayAllMerchant(){
        merchantController.showAllMerchant();
    }

    public void createView(){

    }

    public void updateView(){
        
    }

    public void deleteView(){
        
    }

    public void displayMerchants(){
        
    }

    public void switchMerchant(){

    }


}
