package com.example.FBJV24001115synergy7indbinfoodch6.dto.product;


import com.example.FBJV24001115synergy7indbinfoodch6.models.Product.CategoryProduct;

import lombok.Data;

@Data
public class ProductUpdateDTO {
    private String name;
    private Double price;
    private int stock;
    private CategoryProduct category;
}
