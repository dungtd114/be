package com.example.style_store_be.controller;

import com.example.style_store_be.dto.request.ApiResponse;
import com.example.style_store_be.dto.request.DiaChiNhanRequest;
import com.example.style_store_be.dto.response.DiaChiNhanResponse;
import com.example.style_store_be.entity.DiaChiNhan;
import com.example.style_store_be.entity.User;
import com.example.style_store_be.service.website.DiaChiNhanService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dia-chi-nhan")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class DiaChiNhanContrller {
    DiaChiNhanService diaChiNhanService;

    @PostMapping("/them-dia-chi-nhan")
    public ApiResponse<DiaChiNhan> createDiaChiNhan(@RequestBody DiaChiNhanRequest diaChiNhanRequest) {
        try {
            ApiResponse<DiaChiNhan> apiResponse = new ApiResponse<>();
            apiResponse.setResult(diaChiNhanService.createDiaChiNhan(diaChiNhanRequest));
            return apiResponse;
        } catch (Exception e) {
            log.error("Error when creating staff: ", e);
            throw e;
        }

    }

    @GetMapping("/hien-thi-dia-chi-nhan")
    public List<DiaChiNhan> getAllDiaChiNhan(){
        return diaChiNhanService.getAllDiaChiNhan();
    }

    @GetMapping("/chon-dia-chi-nhan/{id}")
    DiaChiNhanResponse selectedDiaChiNhan(@PathVariable Long id) {
        return diaChiNhanService.selectedDiaChiNhan(id);
    }

    @DeleteMapping("/xoa-dia-chi-nhan/{id}")
    public ApiResponse<String> deleteDiaChiNhan(@PathVariable Long id) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        diaChiNhanService.deleteDiaChiNhan(id);
        apiResponse.setResult("Xóa địa chỉ nhận thành công");
        return apiResponse;
    }
}
