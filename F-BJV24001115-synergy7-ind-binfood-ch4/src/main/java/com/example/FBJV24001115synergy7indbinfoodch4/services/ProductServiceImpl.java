package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FBJV24001115synergy7indbinfoodch4.controllers.MerchantController;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch4.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Override
    public void createdProduct(Product product) {
        Optional<Product> existProduct = Optional.ofNullable(productRepository.findByName(product.getProduct_name()));
        if (existProduct.isPresent()) {
            throw new RuntimeException("Product sudah tersedia");
        }else{
            productRepository.save(product);
        }
    }

    @Override
    public void updateProduct(UUID id, Double price) {
        Optional<Product> existProduct = productRepository.findById(id);
        if (!existProduct.isPresent()) {
            throw new RuntimeException("Product tidak ditemukan!");
        }else{
            Product choosenProduct = existProduct.get();
            choosenProduct.setPrice(price);
            productRepository.save(choosenProduct);
        }
    }

    @Override
    public void deleteProduct(UUID id) {
        Optional<Product> existProduct = productRepository.findById(id);
        if (!existProduct.isPresent()) {
            throw new RuntimeException("Product Tidak Ditemukan");
        }else{
            Product choosenProduct = existProduct.get();
            productRepository.delete(choosenProduct);
        }

    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public UUID getIdProduct(String product_name) {
        Optional<Product> existProduct = Optional.ofNullable(productRepository.findByName(product_name));
        if (!existProduct.isPresent()) {
            throw new RuntimeException("Product Tidak Ditemukan");
        }else{
            Product choosenProduct = existProduct.get();
            return choosenProduct.getId();
        }
    }

    

}
