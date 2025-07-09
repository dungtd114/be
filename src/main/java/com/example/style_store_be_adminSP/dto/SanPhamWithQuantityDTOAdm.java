package com.example.style_store_be_adminSP.dto;

import com.example.style_store_be.entity.SanPham;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SanPhamWithQuantityDTOAdm {
    private SanPham sanPham;
    private Long totalQuantity;
}
