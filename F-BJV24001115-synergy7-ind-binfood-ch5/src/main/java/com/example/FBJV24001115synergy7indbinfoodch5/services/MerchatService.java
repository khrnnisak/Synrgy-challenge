package com.example.FBJV24001115synergy7indbinfoodch5.services;

import java.util.List;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch5.dto.merchant.MerchantCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.dto.merchant.MerchantDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.dto.merchant.MerchantUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.models.Merchant;

public interface MerchatService {

    MerchantDTO createMerchant(MerchantCreateDTO merchantCreateDTO);
    MerchantDTO updateMerchant(UUID id, MerchantUpdateDTO merchantUpdateDTO);
    void deleteMerchant(UUID id);
    List<Merchant> getAllMerchant();
    List<Merchant> getOpenedMerchant();
    List<Merchant> getClosedMerchant();
    MerchantDTO switchMerchant(UUID id);
    MerchantDTO getMerchantById(UUID id);

}
