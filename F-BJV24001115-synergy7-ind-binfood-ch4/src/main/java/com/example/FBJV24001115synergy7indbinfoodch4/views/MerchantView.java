package com.example.FBJV24001115synergy7indbinfoodch4.views;

import java.util.Scanner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.FBJV24001115synergy7indbinfoodch4.controllers.MerchantController;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.AdditionalUtil;

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
        System.out.println("Tambah Merchant");
        System.out.print("Masukkan Nama Merchant : ");
        String nama = input.next();
        System.out.print("Masukkan Lokasi Merchant : ");
        String lokasi = input.next();

        Merchant merchant = Merchant.builder()
                            .name(nama.toLowerCase())
                            .merchant_location(lokasi.toLowerCase())
                            .isOpened(true)
                            .build();
        merchantController.createMerchant(merchant);
        displayMerchantMenu();

    }

    public void updateView(){
        System.out.println("=====================================");
        System.out.print("Merchant yang ingin diubah : ");
        String merchant = input.next();
        System.out.print("Lokasi baru");
        String lokasi = input.next();

        UUID merchant_id = merchantController.getMerchantId(merchant);
        merchantController.updateMerchant(merchant_id, lokasi);
        displayMerchantMenu();
    }

    public void deleteView(){
        System.out.println("==========================");
        System.out.print("Merchant yang ingin dihapus : ");
        String merchant = input.next();

        UUID merchant_id = merchantController.getMerchantId(merchant);
        merchantController.deleteMerchant(merchant_id);
        displayMainMenu();
        
    }

    public void displayMerchants(){
        
    }

    public void switchMerchant(){

    }


}
