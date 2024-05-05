package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;

public interface ProductService {
    void createdProduct(Product product);
    void updateProduct(UUID id, Double price);
    void deleteProduct(UUID id);
    List<Product> getAllProduct();
    UUID getIdProduct(String product_name);
    int countProduct();
    Product getProductById( UUID id);
}
