package com.example.FBJV24001115synergy7indbinfoodch4.controllers;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch4.services.MerchatService;
import com.example.FBJV24001115synergy7indbinfoodch4.views.MainView;
import com.example.FBJV24001115synergy7indbinfoodch4.views.MerchantView;

@Controller

public class MerchantController {

    @Autowired MerchatService merchantService;
    @Autowired MerchantView merchantView;
    @Autowired MainView mainView;

    public void showAllMerchant(){
        List<Merchant> merchants = merchantService.getAllMerchant();
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

    public void createMerchant(Merchant merchant){
        merchantService.createMerchant(merchant);
    }

    public void updateMerchant(UUID id, String location){
        merchantService.updateMerchant(id, location);
    }

    public void deleteMerchant(UUID merchant_id){
        merchantService.deleteMerchant(merchant_id);
    }
    public void showOpenedMerchant(){
        List<Merchant> openedMerchants = merchantService.getOpenedMerchant();
        openedMerchants.forEach(merchant -> System.out.println(merchant.getName()));

    }

    public void showClosedMerchant(){
        List<Merchant> closedMerchants = merchantService.getOpenedMerchant();
        closedMerchants.forEach(merchant -> System.out.println(merchant.getName()));

    }

    public void switchMerchant(String merchantName){
        showAllMerchant();
        merchantService.switchMerchant(merchantName);
        showAllMerchant();
    }

    public UUID getMerchantId(String merchantName){
        return merchantService.getMerchantID(merchantName);
    }

    public void merchantMenuSelection(int choice){
        switch (choice) {
            case 1:
                merchantView.createView();
                break;
            case 2:
                merchantView.updateView();
                break;
            case 3:
                merchantView.deleteView();
                break;
            case 4:
                merchantView.switchMerchant();
                break;
            case 0:
                mainView.displayView();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                mainView.displayView();
                break;
        }
    }

    public Merchant getMerchantById(UUID id){
        return merchantService.getMerchantById(id);
    }

}
