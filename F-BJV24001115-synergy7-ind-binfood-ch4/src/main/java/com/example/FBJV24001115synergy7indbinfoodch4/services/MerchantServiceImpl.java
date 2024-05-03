package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch4.repositories.MerchantRepository;

@Service
public class MerchantServiceImpl implements MerchatService{
    @Autowired 
    MerchantRepository merchantRepository;

    @Override
    public void createMerchant(Merchant merchant) {
        Optional<Merchant> existMerchant = Optional.ofNullable(merchantRepository.findByName(merchant.getMerchant_name()));
        if (existMerchant.isPresent()) {
            System.out.println("Merchant Sudah Tersedia");
        }else{
            merchantRepository.save(merchant);
        }
    }

    @Override
    public void updateMerchant(UUID id, String location) {
        Optional<Merchant> existMerchant = merchantRepository.findById(id);
        if (!existMerchant.isPresent()) {
            throw new RuntimeException();
        }else{
            Merchant choosenMerchant = existMerchant.get();
            choosenMerchant.setMerchant_location(location);
            merchantRepository.save(choosenMerchant);
        }
    }

    @Override
    public void deleteMerchant(UUID id) {
        Optional<Merchant> existMerchant = merchantRepository.findById(id);
        if (!existMerchant.isPresent()) {
            throw new RuntimeException();
        }else{
            Merchant choosenMerchant = existMerchant.get();
            merchantRepository.delete(choosenMerchant);
        }
    }

    @Override
    public List<Merchant> getAllMerchant() {
        return merchantRepository.findAll();
    }

    @Override
    public List<Merchant> getOpenedMerchant() {
        return merchantRepository.findOpenedMerchant();
    }

    @Override
    public List<Merchant> getClosedMerchant() {
        return merchantRepository.findClosedMerchant();
    }

    @Override
    public UUID getMerchantID(String merchant_name) {
        Optional<Merchant> existMerchant = Optional.ofNullable(merchantRepository.findByName(merchant_name));
        if (!existMerchant.isPresent()) {
            throw new RuntimeException();
        }else{
            Merchant merchant = existMerchant.get();
            return merchant.getId();
        }
    }

    @Override
    public void switchMerchant(String merchant_name) {
        Optional<Merchant> existMerchant = Optional.ofNullable(merchantRepository.findByName(merchant_name));
        if (!existMerchant.isPresent()) {
            throw new RuntimeException();
        }else{

            Merchant merchant = existMerchant.get();
            if (Boolean.TRUE.equals(merchant.getIsOpened())) {
                merchant.setIsOpened(false);
            }else{
                merchant.setIsOpened(true);
            }
            merchantRepository.save(merchant);
        }
    }

    
}
