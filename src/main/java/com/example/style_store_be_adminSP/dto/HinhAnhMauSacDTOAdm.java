package com.example.style_store_be_adminSP.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class HinhAnhMauSacDTOAdm {
    private Long id;
    private String hinhAnh;
    private Long mauSacId;
    private String tenMauSac;
    private LocalDateTime ngayXoa;
    private UUID hinhAnhMauSacId;
}