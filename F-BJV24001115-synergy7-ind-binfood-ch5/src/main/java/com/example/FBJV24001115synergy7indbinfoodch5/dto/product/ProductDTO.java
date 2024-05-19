package com.example.FBJV24001115synergy7indbinfoodch5.dto.product;

import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch5.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch5.models.Product.CategoryProduct;

import lombok.Data;

@Data
public class ProductDTO {
    private UUID id;
    private String name;
    private Double price;
    private CategoryProduct category;
    private UUID merchant_id;
}
