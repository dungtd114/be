package com.example.style_store_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DiaChiNhanRequest {

    private String diaChi;

    private String tenNguoiNhan;

    private String soDienThoai;


    private String tinh;

    private String huyen;

    private String xa;

    private String soNha;
}
