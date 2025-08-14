package com.example.style_store_be_adminHD.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonCtAdmDto {
    private Long id;
    private String tenSanPham;
    private Double giaTien;
    private Integer soLuong;
    private Double thanhTien;

    // ID của ChiTietSanPham (nếu bạn không muốn load toàn bộ DTO của sản phẩm)
    private Long idSanPhamCt;

    // THÊM: Trường để chứa URL ảnh sản phẩm, cần ánh xạ từ ChiTietSanPhamAdm
   // private String anhSanPham;
}
