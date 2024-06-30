package com.example.FBJV24001115synergy7indbinfoodch6.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FBJV24001115synergy7indbinfoodch6.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>{

    Product findByName(String product_name);
    List<Product> findByMerchant(Merchant merchant, Pageable pageable);
    Product findByNameAndMerchant(String product_name, Merchant merchant);
}
