package com.example.FBJV24001115synergy7indbinfoodch6.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.FBJV24001115synergy7indbinfoodch6.dto.product.ProductDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.product.ProductUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch6.repositories.ProductRepository;
import com.example.FBJV24001115synergy7indbinfoodch6.utils.FormatMessageUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class ProductServiceImpl implements ProductService{

    @Autowired ProductRepository productRepository;
    @Autowired ModelMapper modelMapper;

    @Override
    public ProductDTO createdProduct(Product product) {
        try {
            Optional<Product> existProduct = Optional.ofNullable(productRepository.findByNameAndMerchant(product.getName(), product.getMerchant()));
            if (existProduct.isPresent()) {
                log.error(FormatMessageUtil.duplicateMessage());
            }else{
                productRepository.save(product);
                log.info(FormatMessageUtil.succesToAddMessage());
                return modelMapper.map(product, ProductDTO.class);

            }
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToAddMessage());
        } 
        return null;
    }

    @Override
    public ProductDTO updateProduct(UUID id, ProductUpdateDTO productUpdateDTO) {
        try {
            Optional<Product> existProduct = productRepository.findById(id);
            if (!existProduct.isPresent()) {
                log.error(FormatMessageUtil.notFoundMessage());
            }else{
                Product choosenProduct = existProduct.get();
                choosenProduct.setPrice(productUpdateDTO.getPrice());
                productRepository.save(choosenProduct);
                log.info(FormatMessageUtil.succesToEditMessage());
                return modelMapper.map(choosenProduct, ProductDTO.class);
            }
        } catch (Exception e) {
            log.error(FormatMessageUtil.failedToAddMessage());
        } 
        return null;
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
            System.out.println(FormatMessageUtil.failedToAddMessage());
        } 

    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
    
    @Override
    public ProductDTO getProductById(UUID id) {
        try {
            Optional<UUID> opt = Optional.ofNullable(id);
            if (!opt.isPresent()) {
               log.error(FormatMessageUtil.errorMessageFormat("Masukan tidak boleh kosong"));
            }
            Optional<Product> existproduct = productRepository.findById(id);
            if (!existproduct.isPresent()) {
                log.error(FormatMessageUtil.notFoundMessage());
            }else{
                Product product = existproduct.get();
                return modelMapper.map(product, ProductDTO.class);

            }
        } catch (Exception e) {
            log.error(FormatMessageUtil.notFoundMessage());
        }
        return null;
    }
    @Override
    public List<Product> getByMerchant(Merchant merchant) {
        Optional<Merchant> opt = Optional.ofNullable(merchant);
        if (!opt.isPresent()) {
            System.out.println(FormatMessageUtil.errorMessageFormat("Masukan tidak boleh kosong"));
        }
        Pageable pageable = PageRequest.of(0, 2);

        List<Product> products = productRepository.findByMerchant(merchant, pageable);
        return products;
    }

    

}
