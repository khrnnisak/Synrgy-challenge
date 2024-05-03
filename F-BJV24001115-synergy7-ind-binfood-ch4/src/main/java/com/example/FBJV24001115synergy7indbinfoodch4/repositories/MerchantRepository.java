package com.example.FBJV24001115synergy7indbinfoodch4.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID>{
    Merchant findByName(String name);

    @Query(value = "select * from merchant where isOpened = true", nativeQuery = true)
    List<Merchant> findOpenedMerchant();

    @Query(value = "select * from merchant where isOpened = false", nativeQuery = true)
    List<Merchant> findClosedMerchant();

}
