package com.example.FBJV24001115synergy7indbinfoodch8.dto.product;

import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch8.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch8.models.Product.CategoryProduct;

import lombok.Data;

@Data
public class ProductCreateDTO {

    private String name;
    private Double price;
    private int stock;
    private CategoryProduct category;
    private UUID merchantId;
}
