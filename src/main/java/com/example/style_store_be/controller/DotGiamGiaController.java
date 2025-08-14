package com.example.style_store_be.controller;


import com.example.style_store_be.dto.GiamGiaDto;
import com.example.style_store_be.dto.request.ApDungGGRequest;
import com.example.style_store_be.dto.request.ApDungGGUpdateRequest;
import com.example.style_store_be.dto.request.ApiResponse;
import com.example.style_store_be.dto.request.GiamGiaRequest;
import com.example.style_store_be.dto.response.GiamGiaResponse;
import com.example.style_store_be.entity.GiamGia;
import com.example.style_store_be.entity.SanPham;
import com.example.style_store_be.service.SanPhamWebService;
import com.example.style_store_be.service.website.GiamGiaService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/admin/giam-gia")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class DotGiamGiaController {
    GiamGiaService giamGiaService;
    SanPhamWebService sanPhamWebService;
    @PostMapping("/them-phieu-giam-gia")
    public ApiResponse<GiamGia> createVoucher( @RequestBody GiamGiaRequest request) {
        ApiResponse<GiamGia> apiResponse = new ApiResponse<>();
        apiResponse.setResult(giamGiaService.createVoucher(request));
        return apiResponse;
    }

    @PostMapping("/ap-phieu-giam-gia")
    public ApiResponse<GiamGia> applyVocher(@Valid @RequestBody ApDungGGRequest request) {
        ApiResponse<GiamGia> apiResponse = new ApiResponse<>();
        apiResponse.setResult(giamGiaService.applyVocher(request));
        return apiResponse;
    }



    @GetMapping("/hien-thi")
    Page<GiamGiaDto> getPageGiamGia(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String tenGiamGia,
            @RequestParam(required = false) Integer idTrangThai,
            @RequestParam(required = false) String giamGia,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayBatDau,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayKetThuc
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GiamGiaDto> giamGiaDtos = giamGiaService.getPageGiamGia(tenGiamGia, idTrangThai, giamGia, ngayBatDau, ngayKetThuc, pageable);
        return giamGiaDtos;
    }

    @GetMapping("/chi-tiet/{id}")
    public ApiResponse<GiamGiaResponse> detailGiamGia(@PathVariable Long id) {
        ApiResponse<GiamGiaResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(giamGiaService.detailGiamGia(id));
        return apiResponse;
    }

    @PutMapping("/cap-nhat/{id}")
    GiamGiaResponse updateGiamGia(@PathVariable Long id,@Valid @RequestBody ApDungGGUpdateRequest apDungGGUpdateRequest){
        return giamGiaService.updateGiamGia(id,apDungGGUpdateRequest);
    }

}
