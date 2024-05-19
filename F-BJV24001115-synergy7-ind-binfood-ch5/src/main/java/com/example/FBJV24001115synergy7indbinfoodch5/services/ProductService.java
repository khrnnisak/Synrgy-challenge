package com.example.FBJV24001115synergy7indbinfoodch5.services;

import java.util.List;
import java.util.UUID;

import com.example.FBJV24001115synergy7indbinfoodch5.dto.product.ProductCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.dto.product.ProductDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.dto.product.ProductUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch5.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch5.models.Product;

public interface ProductService {
    ProductDTO createdProduct(Product product);
    ProductDTO updateProduct(UUID id, ProductUpdateDTO productUpdateDTO);
    void deleteProduct(UUID id);
    List<Product> getAllProduct();
    ProductDTO getProductById( UUID id);
    List<Product> getByMerchant(Merchant merchant);

}
