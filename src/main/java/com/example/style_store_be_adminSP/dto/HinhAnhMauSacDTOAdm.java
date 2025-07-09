package com.example.style_store_be_adminSP.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HinhAnhMauSacDTOAdm {
    private Long id;
    private String hinhAnh;
    private Long mauSacId;
    private String tenMauSac;
}