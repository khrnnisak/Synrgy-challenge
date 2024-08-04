package com.example.FBJV24001115synergy7indbinfoodch8.dto.product;

import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch8.dto.merchant.MerchantDTO;
import com.example.FBJV24001115synergy7indbinfoodch8.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch8.models.Product.CategoryProduct;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private String name;
    private Double price;
    private String merchant;
}
