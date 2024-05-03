package com.example.FBJV24001115synergy7indbinfoodch4.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch4.services.ProductService;

public class ProductController {

    @Autowired
    ProductService productService;

    public void showAllProducts(){
        List<Product> products = productService.getAllProduct();
        products.forEach(product -> System.out.println(product.getProduct_name() + "||" + product.getPrice()));
    }

    public void createProduct(Product product){
        productService.createdProduct(product);
    }

    public void updateProduct(String product_name, Double price){
        showAllProducts();
        UUID product_id = productService.getIdProduct(product_name);
        productService.updateProduct(product_id, price);
        showAllProducts();
    }

    public void deleteProduct(String product_name){
        showAllProducts();
        UUID product_id = productService.getIdProduct(product_name);
        productService.deleteProduct(product_id);
        showAllProducts();
    }

    public UUID getProductId(String productName){
        return productService.getIdProduct(productName);
    }


}
