package com.example.style_store_be.controller;

import com.example.style_store_be.dto.GiamGiaDto;
import com.example.style_store_be.dto.HoaDonCtDto;
import com.example.style_store_be.dto.LichSuDonHangDto;
import com.example.style_store_be.dto.request.ApiResponse;
import com.example.style_store_be.dto.request.DonHangRequest;
import com.example.style_store_be.entity.HoaDon;
import com.example.style_store_be.service.website.DonHangService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/don-hang")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class DonHangController {
    DonHangService donHangService;

    @PostMapping("/dat-hang-online-chua-thanh-toan")
    public ApiResponse<HoaDon> createrDonHang (@RequestBody DonHangRequest request){
        ApiResponse<HoaDon> apiResponse = new ApiResponse<>();
        apiResponse.setResult(donHangService.createrDonHang(request));
        return apiResponse;
    }


    @GetMapping("/lich-su-dat-hang")
    public ApiResponse<Page<LichSuDonHangDto>> getHistoryDonHang(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer trangThaiDonHang,
            @RequestParam(required = false) Integer trangThaiThanhToan,
            @RequestParam(required = false) Integer phuongThucThanhToan,
            @RequestParam(required = false) String maDonHang,
            @RequestParam(required = false) String tenSanPham,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date tuNgay,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date denNgay
    ) {
        System.out.println("tuNgay: " + tuNgay);
        System.out.println("denNgay: " + denNgay);
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<Page<LichSuDonHangDto>> apiResponse = new ApiResponse<>();
        Page<LichSuDonHangDto> result = donHangService.getLichSuDatHang( trangThaiDonHang, trangThaiThanhToan, phuongThucThanhToan,
                maDonHang, tenSanPham, tuNgay, denNgay, pageable
        );
        apiResponse.setResult(result);
        return apiResponse;
    }

    @GetMapping("/chi-tiet-don-hang/{id}")
    public ApiResponse<List<HoaDonCtDto>> getChiTietDonHang(
            @PathVariable Long id,
            @RequestParam(required = false) String tenSanPham
    ) {
        ApiResponse<List<HoaDonCtDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(donHangService.getChiTietDonHang(id, tenSanPham));
        return apiResponse;
    }




}
