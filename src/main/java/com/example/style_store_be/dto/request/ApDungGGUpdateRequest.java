package com.example.style_store_be.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ApDungGGUpdateRequest {

    private Long idGiamGia;
    private List<Long> sanPhamCtIds;

}
