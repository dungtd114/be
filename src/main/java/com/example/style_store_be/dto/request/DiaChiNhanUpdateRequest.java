package com.example.style_store_be.dto.request;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DiaChiNhanUpdateRequest {

    private String diaChi;

    private String tenNguoiNhan;

    private String soDienThoai;

    private String tinh;

    private String huyen;

    private String xa;

    private String soNha;
}
