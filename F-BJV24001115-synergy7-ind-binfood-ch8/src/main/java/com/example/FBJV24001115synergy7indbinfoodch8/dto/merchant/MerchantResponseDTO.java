package com.example.FBJV24001115synergy7indbinfoodch8.dto.merchant;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantResponseDTO {
    private UUID id;
    private String name;
    private String location;
    private boolean opened;
}
