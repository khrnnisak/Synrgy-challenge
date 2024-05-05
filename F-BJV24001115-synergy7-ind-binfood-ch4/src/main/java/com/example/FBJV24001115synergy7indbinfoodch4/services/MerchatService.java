package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Merchant;

public interface MerchatService {

    void createMerchant(Merchant merchant);
    void updateMerchant(UUID id, String destination);
    void deleteMerchant(UUID id);
    List<Merchant> getAllMerchant();
    List<Merchant> getOpenedMerchant();
    List<Merchant> getClosedMerchant();
    UUID getMerchantID(String merchant_name);
    void switchMerchant(String merchant_name);
    Merchant getMerchantById(UUID id);

}
