package com.example.style_store_be.controller;

import com.example.style_store_be.dto.HoaDonAdminDto;
import com.example.style_store_be.dto.LichSuDonHangDto;
import com.example.style_store_be.dto.LichSuHoaDonDto;
import com.example.style_store_be.dto.request.ApiResponse;
import com.example.style_store_be.dto.request.DonHangRequest;
import com.example.style_store_be.dto.request.HoaDonChiTietRequest;
import com.example.style_store_be.dto.request.HoaDonUpdateRequest;
import com.example.style_store_be.dto.response.HoaDonAdminResponse;
import com.example.style_store_be.dto.response.HoaDonUDResponse;
import com.example.style_store_be.dto.response.UserResponse;
import com.example.style_store_be.entity.HoaDon;
import com.example.style_store_be.entity.HoaDonCt;
import com.example.style_store_be.entity.LichSuHoaDon;
import com.example.style_store_be.service.HoaDonAdminService;
import com.example.style_store_be.service.HoaDonChiTietService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/hoa-don-admin")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class HoaDonAdminController {
    HoaDonAdminService hoaDonAdminService;
    HoaDonChiTietService hoaDonChiTietService;
    @GetMapping("/danh-sach-hoa-don")
    public ApiResponse<Page<HoaDonAdminDto>> getPageHoaDonAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String maHoaDonOrTenKhachHang0rSoDienThoai,
            @RequestParam(required = false) Integer trangThaiDonHang,
            @RequestParam(required = false) Integer trangThaiThanhToan,
            @RequestParam(required = false) Integer phuongThucThanhToan
    ) {

        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<Page<HoaDonAdminDto>> apiResponse = new ApiResponse<>();
        Page<HoaDonAdminDto> result = hoaDonAdminService.getPageHoaDonAdmin( maHoaDonOrTenKhachHang0rSoDienThoai, trangThaiDonHang, trangThaiThanhToan,
                phuongThucThanhToan, pageable
        );
        apiResponse.setResult(result);
        return apiResponse;
    }

    @GetMapping("/chi-tiet/{id}")
    public ApiResponse<HoaDonAdminResponse> getHoaDonAdminDetail(@PathVariable Long id) {
        ApiResponse<HoaDonAdminResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(hoaDonAdminService.getHoaDonAdminDetail(id));
        return apiResponse;
    }

    @PostMapping("/them-san-pham-hoa-don")
    public ApiResponse<HoaDonCt> addSanPhamHoaDon (@RequestBody HoaDonChiTietRequest request){
        ApiResponse<HoaDonCt> apiResponse = new ApiResponse<>();
        apiResponse.setResult(hoaDonChiTietService.addSanPhamHoaDon(request));
        return apiResponse;
    }

    @DeleteMapping("/xoa-san-pham-hoa-don/{id}")
    String deleteSanPhamHoaDon(@PathVariable Long id) {
        hoaDonChiTietService.deleteSanPhamHoaDon(id);
        return "Xóa sản phẩm hóa đơn thành công";
    }

    @GetMapping("/chi-tiet-thong-tin-van-chuyen/{id}")
    public ApiResponse<HoaDonUDResponse> getHoaDonUDDetail(@PathVariable Long id) {
        ApiResponse<HoaDonUDResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(hoaDonAdminService.getHoaDonUDDetail(id));
        return apiResponse;
    }

    @PutMapping("/cap-nhat-thong-tin-van-chuyen/{id}")
    HoaDonUDResponse updateHoaDonUDDetail(@PathVariable Long id, @RequestBody HoaDonUpdateRequest request) {
        return hoaDonAdminService.updateHoaDonUDDetail(id,request);
    }

    @GetMapping("/chuyen-trang-thai-don-hang/{id}")
    public String chuyenTrangThaiDonHang(@PathVariable Long id) {
        hoaDonAdminService.chuyenTrangThaiDonHang(id);
        return "Chuyển trạng thái đơn hàng thành công";
    }

    @GetMapping("/lich-su-don-hang/{id}")
    public List<LichSuHoaDonDto> getListLichSu(@PathVariable Long id){
        return hoaDonAdminService.getListLichSu(id);
    }

    @GetMapping("/huy-don-hang/{id}")
    public String huyDonHang(@PathVariable Long id) {
        hoaDonAdminService.huyDonHang(id);
        return "Chuyển trạng thái đơn hàng thành công";
    }


}
