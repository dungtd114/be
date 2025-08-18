package com.example.style_store_be.dto;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichSuHoaDonDto {
    private Long id;

    private Long idHoaDon;

    private String tieuDe;

    private String nguoiThucHien;

    private String noiDung;

    private Date ngayCapNhat;
}
