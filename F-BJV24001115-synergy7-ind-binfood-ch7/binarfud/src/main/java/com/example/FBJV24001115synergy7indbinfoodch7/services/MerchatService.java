package com.example.FBJV24001115synergy7indbinfoodch7.services;

import java.util.List;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch7.dto.merchant.MerchantDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.merchant.MerchantResponseDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.merchant.MerchantUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.models.Merchant;

public interface MerchatService {

    MerchantResponseDTO createMerchant(MerchantDTO merchantCreateDTO);
    MerchantResponseDTO updateMerchant(UUID id, MerchantUpdateDTO merchantUpdateDTO);
    void deleteMerchant(UUID id);
    List<MerchantResponseDTO> getAllMerchant();
    List<MerchantResponseDTO> getOpenedMerchant();
    List<MerchantResponseDTO> getClosedMerchant();
    MerchantResponseDTO switchMerchant(UUID id);
    MerchantResponseDTO getMerchantById(UUID id);

}
