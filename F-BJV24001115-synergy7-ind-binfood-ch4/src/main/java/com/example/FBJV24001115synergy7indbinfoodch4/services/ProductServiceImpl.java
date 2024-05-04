package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch4.repositories.ProductRepository;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.FormatMessageUtil;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Override
    public void createdProduct(Product product) {
        try {
            Optional<Product> existProduct = Optional.ofNullable(productRepository.findByName(product.getName()));
            if (existProduct.isPresent()) {
                System.out.println(FormatMessageUtil.duplicateMessage());
            }else{
                productRepository.save(product);
                System.out.println(FormatMessageUtil.succesToAddMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToAddMessage() + e);
        } 
    }

    @Override
    public void updateProduct(UUID id, Double price) {
        try {
            Optional<Product> existProduct = productRepository.findById(id);
            if (!existProduct.isPresent()) {
                System.out.println(FormatMessageUtil.notFoundMessage());
            }else{
                Product choosenProduct = existProduct.get();
                choosenProduct.setPrice(price);
                productRepository.save(choosenProduct);
                System.out.println(FormatMessageUtil.succesToEditMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToAddMessage() + e);
        } 
        
    }

    @Override
    public void deleteProduct(UUID id) {
        try {
            Optional<Product> existProduct = productRepository.findById(id);
            if (!existProduct.isPresent()) {
                System.out.println(FormatMessageUtil.notFoundMessage());
            }else{
                Product choosenProduct = existProduct.get();
                productRepository.delete(choosenProduct);
                System.out.println(FormatMessageUtil.succesToDeleteMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToAddMessage() + e);
        } 

    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public UUID getIdProduct(String product_name) {
        try {
            Optional<Product> existProduct = Optional.ofNullable(productRepository.findByName(product_name));
            if (!existProduct.isPresent()) {
                System.out.println(FormatMessageUtil.notFoundMessage());
            }else{
                Product choosenProduct = existProduct.get();
                return choosenProduct.getId();
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToAddMessage() + e);
        } 
        return null;
    }

    

}
