package com.example.style_store_be.controller;

import com.example.style_store_be.dto.request.ApiResponse;
import com.example.style_store_be.dto.request.DonHangRequest;
import com.example.style_store_be.entity.HoaDon;
import com.example.style_store_be.service.website.DonHangService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
