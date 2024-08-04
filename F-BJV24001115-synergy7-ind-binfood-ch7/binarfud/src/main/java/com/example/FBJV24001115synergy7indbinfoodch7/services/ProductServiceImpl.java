package com.example.FBJV24001115synergy7indbinfoodch7.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.FBJV24001115synergy7indbinfoodch7.dto.merchant.MerchantDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.merchant.MerchantResponseDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.product.ProductCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.product.ProductDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.product.ProductUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.mapper.MerchantMapper;
import com.example.FBJV24001115synergy7indbinfoodch7.mapper.ProductMapper;
import com.example.FBJV24001115synergy7indbinfoodch7.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch7.models.OrderDetail;
import com.example.FBJV24001115synergy7indbinfoodch7.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch7.models.Product.CategoryProduct;
import com.example.FBJV24001115synergy7indbinfoodch7.repositories.MerchantRepository;
import com.example.FBJV24001115synergy7indbinfoodch7.repositories.ProductRepository;
import com.example.FBJV24001115synergy7indbinfoodch7.utils.FormatMessageUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class ProductServiceImpl implements ProductService{

    @Autowired ProductRepository productRepository;
    @Autowired ModelMapper modelMapper;
    @Autowired ProductMapper productMapper;
    @Autowired MerchantMapper merchantMapper;
    @Autowired MerchantRepository merchantRepository;

    @Override
    public ProductDTO createdProduct(ProductCreateDTO productCreateDTO) {
        
        try {
            Merchant merchant = merchantRepository.findById(productCreateDTO.getMerchantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Merchant not found"));

            Optional<Product> existProduct = Optional.ofNullable(productRepository.findByNameAndMerchant(productCreateDTO.getName(), merchant));

            if (existProduct.isPresent()) {
                log.error(FormatMessageUtil.duplicateMessage());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product Already Exist");
            }else{
                Product newProduct = Product.builder()
                    .name(productCreateDTO.getName())
                    .category(CategoryProduct.valueOf(productCreateDTO.getCategory().toString().toUpperCase()))
                    .price(productCreateDTO.getPrice())
                    .stock(productCreateDTO.getStock())
                    .merchant(merchant)
                    .build();
                productRepository.save(newProduct);
                newProduct.setId(newProduct.getId());
                MerchantDTO merchantDTO = merchantMapper.toMerchantDto(merchant);
                log.info(FormatMessageUtil.succesToAddMessage());
                return productMapper.toProductDTO(newProduct, merchantDTO);

            }
        }catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        } 
    }

    @Override
    public ProductDTO updateProduct(UUID id, ProductUpdateDTO productUpdateDTO) {
        try {
            Product existProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found"));
            existProduct.setName(productUpdateDTO.getName());
            existProduct.setCategory(productUpdateDTO.getCategory());
            existProduct.setPrice(productUpdateDTO.getPrice());
            existProduct.setStock(productUpdateDTO.getStock());
            productRepository.save(existProduct);
            MerchantDTO merchant = merchantMapper.toMerchantDto(existProduct.getMerchant());
            log.info(FormatMessageUtil.succesToEditMessage());
            return productMapper.toProductDTO(existProduct, merchant);
        }catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        } 
    }

    @Override
    public void deleteProduct(UUID id) {
        try {
            Product existProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found"));
            productRepository.delete(existProduct);
            log.info(FormatMessageUtil.succesToDeleteMessage());
        } catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        } 

    }

    @Override
    public List<ProductDTO> getAllProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productList = products
                .stream()
                .map(product -> productMapper.toProductDTO(product, merchantMapper.toMerchantDto(product.getMerchant())))
                .collect(Collectors.toList());
        return productList;
    }
    
    @Override
    public ProductDTO getProductById(UUID id) {
        try {
            Product existProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found"));
            return productMapper.toProductDTO(existProduct, merchantMapper.toMerchantDto(existProduct.getMerchant()));
        } catch (ResponseStatusException e) {
            throw e; 
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        } 
    }
    @Override
    public List<ProductDTO> getByMerchant(UUID merchantId) {
        Merchant existMerchant = merchantRepository.findById(merchantId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Merchant not found"));
        Pageable pageable = PageRequest.of(0, 2);
        List<Product> products = productRepository.findByMerchant(existMerchant, pageable);
        List<ProductDTO> productList = products
                .stream()
                .map(product -> productMapper.toProductDTO(product, merchantMapper.toMerchantDto(product.getMerchant())))
                .collect(Collectors.toList());
        return productList;
    }

    @Override
    public void updateStock(List<OrderDetail> orderDetails, String status) {
        orderDetails.forEach(orderDetail -> {
            Product product = productRepository.findById(orderDetail.getProduct().getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found"));
            if (status.equalsIgnoreCase("cancel")) {
                product.setStock(product.getStock() + orderDetail.getQuantity());
            }else if(status.equalsIgnoreCase("proses")){
                product.setStock(product.getStock() - orderDetail.getQuantity());
            }
            productRepository.save(product);
        });
    }
}
