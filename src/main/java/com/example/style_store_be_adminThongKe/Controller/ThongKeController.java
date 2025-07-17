package com.example.style_store_be_adminThongKe.Controller;

import com.example.style_store_be_adminThongKe.DTO.DoanhThuDTO;
import com.example.style_store_be_adminThongKe.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thong-ke")
@CrossOrigin("*")
public class ThongKeController {

    @Autowired
    private ThongKeService thongKeService;

    @GetMapping("/doanh-thu/ngay")
    public ResponseEntity<?> thongKeDoanhThuTheoNgay(
            @RequestParam(value = "date", required = false) String date) {
        try {
            List<DoanhThuDTO> result;
            if (date == null || date.isEmpty()) {
                result = thongKeService.thongKeDoanhThuTheoNgay();
            } else {
                if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    return ResponseEntity.badRequest()
                            .body(new ErrorResponse(400, "Invalid date format. Use YYYY-MM-DD."));
                }
                result = thongKeService.thongKeDoanhThuTheoNgay(date);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error fetching daily revenue: " + e.getMessage()));
        }
    }

    @GetMapping("/doanh-thu/tuan")
    public ResponseEntity<?> thongKeDoanhThuTheoTuan() {
        try {
            return ResponseEntity.ok(thongKeService.thongKeDoanhThuTheoTuan());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error fetching weekly revenue: " + e.getMessage()));
        }
    }

    @GetMapping("/doanh-thu/thang")
    public ResponseEntity<?> thongKeDoanhThuTheoThang() {
        try {
            return ResponseEntity.ok(thongKeService.thongKeDoanhThuTheoThang());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error fetching monthly revenue: " + e.getMessage()));
        }
    }

    @GetMapping("/doanh-thu/nam")
    public ResponseEntity<?> thongKeDoanhThuTheoNam() {
        try {
            return ResponseEntity.ok(thongKeService.thongKeDoanhThuTheoNam());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error fetching yearly revenue: " + e.getMessage()));
        }
    }

    @GetMapping("/san-pham-ban-chay")
    public ResponseEntity<?> thongKeSanPhamBanChay() {
        try {
            return ResponseEntity.ok(thongKeService.thongKeSanPhamBanChay());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error fetching top products: " + e.getMessage()));
        }
    }
}

class ErrorResponse {
    private int code;
    private String message;

    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}