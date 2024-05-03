package com.example.FBJV24001115synergy7indbinfoodch4.controllers;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch4.services.MerchatService;

public class MerchantController {

    @Autowired
    MerchatService merchantService;

    public void showAllMerchant(){
        List<Merchant> merchants = merchantService.getAllMerchant();
        for (Merchant merchant : merchants) {
            String status;
            if (merchant.getIsOpened() == true) {
                status = "Buka";
            }else{
                status = "Tutup";
            }
            System.out.println(merchant.getMerchant_name() + " (" + status + ")");
        }
    }

    public void createMerchant(Merchant merchant){
        merchantService.createMerchant(merchant);
    }

    public void updateMerchant(String merchantName, String location){
        showAllMerchant();
        UUID merchant_id = merchantService.getMerchantID(merchantName);
        merchantService.updateMerchant(merchant_id, location);
        showAllMerchant();
    }

    public void deleteMerchant(String merchantName){
        showAllMerchant();
        UUID merchant_id = merchantService.getMerchantID(merchantName);
        merchantService.deleteMerchant(merchant_id);
        showAllMerchant();
    }
    public void showOpenedMerchant(){
        List<Merchant> openedMerchants = merchantService.getOpenedMerchant();
        openedMerchants.forEach(merchant -> System.out.println(merchant.getMerchant_name()));

    }

    public void showClosedMerchant(){
        List<Merchant> closedMerchants = merchantService.getOpenedMerchant();
        closedMerchants.forEach(merchant -> System.out.println(merchant.getMerchant_name()));

    }

    public void switchMerchant(String merchantName){
        showAllMerchant();
        merchantService.switchMerchant(merchantName);
        showAllMerchant();
    }

    public UUID getMerchantId(String merchantName){
        return merchantService.getMerchantID(merchantName);
    }

}
