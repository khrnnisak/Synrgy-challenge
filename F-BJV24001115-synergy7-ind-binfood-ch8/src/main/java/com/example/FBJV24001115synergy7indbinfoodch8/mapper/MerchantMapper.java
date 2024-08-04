package com.example.FBJV24001115synergy7indbinfoodch8.mapper;

import org.springframework.stereotype.Component;

import com.example.FBJV24001115synergy7indbinfoodch8.dto.merchant.MerchantDTO;
import com.example.FBJV24001115synergy7indbinfoodch8.dto.merchant.MerchantResponseDTO;
import com.example.FBJV24001115synergy7indbinfoodch8.models.Merchant;

@Component
public class MerchantMapper {
    public MerchantResponseDTO toMerchantResponseDTO(Merchant merchant){
        return MerchantResponseDTO.builder()
            .id(merchant.getId())
            .name(merchant.getName())
            .location(merchant.getLocation())
            .opened(merchant.isOpened())
            .build();
    }

    public MerchantDTO toMerchantDto(Merchant merchant){
        return MerchantDTO.builder()
            .name(merchant.getName())
            .location(merchant.getLocation())
            .build();
    }

}
