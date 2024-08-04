package com.example.FBJV24001115synergy7indbinfoodch7.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.FBJV24001115synergy7indbinfoodch7.dto.merchant.MerchantDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.product.ProductDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.product.ProductResponse;
import com.example.FBJV24001115synergy7indbinfoodch7.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch7.models.Product;

@Component
public class ProductMapper {

    public ProductDTO toProductDTO(Product product, MerchantDTO merchantDTO){
        return ProductDTO.builder()
            .id(product.getId())
            .name(product.getName())
            .category(product.getCategory())
            .price(product.getPrice())
            .stock(product.getStock())
            .merchant(merchantDTO)
            .build();
    }

    public ProductResponse toProductResponse(Product product){
        return ProductResponse.builder()
            .name(product.getName())
            .price(product.getPrice())
            .merchant(product.getMerchant().getName())
            .build();
    }

    

}
