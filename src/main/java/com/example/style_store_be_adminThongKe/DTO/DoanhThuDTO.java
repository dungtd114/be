package com.example.style_store_be_adminThongKe.DTO;

import lombok.Data;

@Data
public class DoanhThuDTO {
    private String thoiGian; // Có thể là ngày, tuần, tháng, hoặc năm
    private Double tongDoanhThu;

    public DoanhThuDTO(String thoiGian, Double tongDoanhThu) {
        this.thoiGian = thoiGian;
        this.tongDoanhThu = tongDoanhThu;
    }
}
