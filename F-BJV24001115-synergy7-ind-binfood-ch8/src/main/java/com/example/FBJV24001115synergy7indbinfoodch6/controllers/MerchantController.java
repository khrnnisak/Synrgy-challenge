package com.example.FBJV24001115synergy7indbinfoodch6.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FBJV24001115synergy7indbinfoodch6.dto.merchant.MerchantCreateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.merchant.MerchantDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.dto.merchant.MerchantUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch6.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch6.services.MerchatService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("merchant")
public class MerchantController {

    @Autowired MerchatService merchantService;
    @Autowired ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<List<MerchantDTO>> showAllMerchant(){
        List<Merchant> merchants = merchantService.getAllMerchant();
        List<MerchantDTO> merchantList = merchants
                .stream()
                .map(merchant -> modelMapper.map(merchant, MerchantDTO.class))
                .toList();
        return new ResponseEntity<>(merchantList, HttpStatus.OK);
    }

    @PostMapping("add")
    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    public ResponseEntity<Map<String, Object>> createMerchant(@RequestBody MerchantCreateDTO merchantCreateDTO){
        Map<String, Object> response = new HashMap<>();

        MerchantDTO merchant = merchantService.createMerchant(merchantCreateDTO);
        response.put("status", "suscces");
        response.put("data", merchant);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    public ResponseEntity<Map<String, Object>> updateMerchant(@PathVariable("id") UUID id, @RequestBody MerchantUpdateDTO merchantUpdateDTO){
        Map<String, Object> response = new HashMap<>();

        MerchantDTO merchant = merchantService.updateMerchant(id, merchantUpdateDTO);
        response.put("status", "suscces");
        response.put("data", merchant);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    public ResponseEntity<Void> deleteMerchant(@PathVariable("id") UUID id){
        merchantService.deleteMerchant(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("opened")
    public ResponseEntity<List<MerchantDTO>> showOpenedMerchant(){
        List<Merchant> merchants = merchantService.getOpenedMerchant();
        List<MerchantDTO> merchantList = merchants
                .stream()
                .map(merchant -> modelMapper.map(merchant, MerchantDTO.class))
                .toList();
        return new ResponseEntity<>(merchantList, HttpStatus.OK);

    }

    @GetMapping("closed")
    public ResponseEntity<List<MerchantDTO>> showClosedMerchant(){
        List<Merchant> merchants = merchantService.getClosedMerchant();
        List<MerchantDTO> merchantList = merchants
                .stream()
                .map(merchant -> modelMapper.map(merchant, MerchantDTO.class))
                .toList();
        return new ResponseEntity<>(merchantList, HttpStatus.OK);

    }

    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    public ResponseEntity<Map<String, Object>> switchMerchant(@PathVariable("id") UUID id){

        Map<String, Object> response = new HashMap<>();
        MerchantDTO merchant = merchantService.switchMerchant(id);
        response.put("status", "suscces");
        response.put("data", merchant);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("by-id/{id}")
    public ResponseEntity<MerchantDTO> getMerchantById(@PathVariable("id") UUID id){
        MerchantDTO merchant =  merchantService.getMerchantById(id);
        return new ResponseEntity<>(merchant, HttpStatus.OK);
    }

}
