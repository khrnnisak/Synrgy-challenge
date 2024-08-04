package com.example.FBJV24001115synergy7indbinfoodch7.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

import com.example.FBJV24001115synergy7indbinfoodch7.dto.merchant.MerchantDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.merchant.MerchantResponseDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.dto.merchant.MerchantUpdateDTO;
import com.example.FBJV24001115synergy7indbinfoodch7.models.Merchant;
import com.example.FBJV24001115synergy7indbinfoodch7.services.MerchatService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("merchant")
public class MerchantController {

    @Autowired MerchatService merchantService;
    @Autowired ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<Map<String, Object>> showAllMerchant(){

        Map<String, Object> response = new HashMap<>();
        List<MerchantResponseDTO> merchantList = merchantService.getAllMerchant();
        response.put("status", "success");
        if (merchantList.isEmpty()) {
            response.put("data", null);
            response.put("message", "Merchant is empty");
        }else{
            response.put("data", merchantList);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("add")
    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    public ResponseEntity<Map<String, Object>> createMerchant(@RequestBody MerchantDTO merchantCreateDTO){
        Map<String, Object> response = new HashMap<>();

        MerchantResponseDTO merchant = merchantService.createMerchant(merchantCreateDTO);
        response.put("status", "success");
        response.put("data", merchant);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    public ResponseEntity<Map<String, Object>> updateMerchant(@PathVariable("id") UUID id, @RequestBody MerchantUpdateDTO merchantUpdateDTO){
        Map<String, Object> response = new HashMap<>();

        MerchantResponseDTO merchant = merchantService.updateMerchant(id, merchantUpdateDTO);
        response.put("status", "suscces");
        response.put("data", merchant);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    public ResponseEntity<String> deleteMerchant(@PathVariable("id") UUID id){
        merchantService.deleteMerchant(id);
        return ResponseEntity.ok("Successfully deleted");
    }

    @GetMapping("opened")
    public ResponseEntity<Map<String, Object>>  showOpenedMerchant(){
        Map<String, Object> response = new HashMap<>();
        List<MerchantResponseDTO> merchantList = merchantService.getOpenedMerchant();
        response.put("status", "success");
        if (merchantList.isEmpty()) {
            response.put("data", null);
            response.put("message", "Merchant is empty");
        }else{
            response.put("data", merchantList);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("closed")
    public ResponseEntity<Map<String, Object>>  showClosedMerchant(){
        Map<String, Object> response = new HashMap<>();
        List<MerchantResponseDTO> merchantList = merchantService.getClosedMerchant();
        response.put("status", "success");
        if (merchantList.isEmpty()) {
            response.put("data", null);
            response.put("message", "Merchant is empty");
        }else{
            response.put("data", merchantList);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    public ResponseEntity<Map<String, Object>> switchMerchant(@PathVariable("id") UUID id){

        Map<String, Object> response = new HashMap<>();
        MerchantResponseDTO merchant = merchantService.switchMerchant(id);
        response.put("status", "suscces");
        response.put("data", merchant);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("by-id/{id}")
    public ResponseEntity<MerchantResponseDTO> getMerchantById(@PathVariable("id") UUID id){
        MerchantResponseDTO merchant =  merchantService.getMerchantById(id);
        return new ResponseEntity<>(merchant, HttpStatus.OK);
    }

}
