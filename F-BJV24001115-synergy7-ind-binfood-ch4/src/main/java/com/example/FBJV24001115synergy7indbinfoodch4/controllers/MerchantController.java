package com.example.FBJV24001115synergy7indbinfoodch4.controllers;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch4.services.MerchatService;
import com.example.FBJV24001115synergy7indbinfoodch4.views.MainView;
import com.example.FBJV24001115synergy7indbinfoodch4.views.MerchantView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MerchantController {

    @Autowired MerchatService merchantService;
    @Autowired MerchantView merchantView;
    @Autowired MainView mainView;

    public List<Merchant> showAllMerchant(){
        return merchantService.getAllMerchant();
    }

    public void createMerchant(String name, String lokasi){
        
        Merchant merchant = Merchant.builder()
                            .name(name.toLowerCase())
                            .merchant_location(lokasi.toLowerCase())
                            .isOpened(true)
                            .build();
        merchantService.createMerchant(merchant);
        merchantView.displayMerchantMenu();
    }

    public void updateMerchant(String nama, String lokasi){
        UUID merchant_id = getMerchantId(nama);
        merchantService.updateMerchant(merchant_id, lokasi);
        merchantView.displayMerchantMenu();

    }

    public void deleteMerchant(String merchant){
        UUID merchant_id = getMerchantId(merchant);
        merchantService.deleteMerchant(merchant_id);
        merchantView.displayMainMenu();
    }
    public List<Merchant> showOpenedMerchant(){
        return merchantService.getOpenedMerchant();

    }

    public List<Merchant> showClosedMerchant(){
        return merchantService.getClosedMerchant();

    }

    public void switchMerchant(String merchantName){
        merchantService.switchMerchant(merchantName);
        merchantView.displayMainMenu();
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
                log.error("Pilihan tidak valid.");
                mainView.displayView();
                break;
        }
    }

    public Merchant getMerchantById(UUID id){
        return merchantService.getMerchantById(id);
    }

}
