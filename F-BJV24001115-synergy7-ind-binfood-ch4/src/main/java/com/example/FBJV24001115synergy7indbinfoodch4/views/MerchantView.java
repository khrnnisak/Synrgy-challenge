package com.example.FBJV24001115synergy7indbinfoodch4.views;

import java.util.List;
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
        merchantController.createMerchant(nama, lokasi);
    }

    public void updateView(){
        System.out.println("=====================================");
        System.out.print("Merchant yang ingin diubah : ");
        String merchant = input.next();
        System.out.print("Lokasi baru");
        String lokasi = input.next();
        merchantController.updateMerchant(merchant, lokasi);
    }

    public void deleteView(){
        System.out.println("==========================");
        System.out.print("Merchant yang ingin dihapus : ");
        String merchant = input.next();
        System.out.print("Apakah anda yakin? (Y/N) ");
        String confirm = input.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            merchantController.deleteMerchant(merchant);

        }
        
    }

    public void displayMerchants(){
        List<Merchant> merchants = merchantController.showAllMerchant();
        merchantFormatDisplay(merchants);
    }

    public void merchantFormatDisplay(List<Merchant> merchants){
        for (Merchant merchant : merchants) {
            String status;
            if (merchant.getIsOpened() == true) {
                status = "Buka";
            }else{
                status = "Tutup";
            }
            System.out.println(merchant.getName() + " (" + status + ")");
        }
    }


    public void switchMerchant(){
        System.out.println(AdditionalUtil.headerFormat("Opened Merchant"));
        List<Merchant> openedMerchants = merchantController.showOpenedMerchant();
        merchantFormatDisplay(openedMerchants);
        System.out.println("=========================================================");
        System.out.println();

        System.out.println(AdditionalUtil.headerFormat("Closed Merchant"));
        List<Merchant> closedMerchants = merchantController.showClosedMerchant();
        merchantFormatDisplay(closedMerchants);
        System.out.println("=========================================================");
        System.out.println();

        System.out.print("Merchant yang ingin anda ubah : ");
        String merchant = input.next();
        merchantController.switchMerchant(merchant);
    }


}
