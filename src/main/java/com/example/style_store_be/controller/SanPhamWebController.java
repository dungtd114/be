package com.example.style_store_be.controller;

import com.example.style_store_be.dto.SanPhamWebDto;
import com.example.style_store_be.dto.request.*;
import com.example.style_store_be.dto.response.HoaDonUDResponse;
import com.example.style_store_be.dto.response.SanPhamAdminResponse;
import com.example.style_store_be.dto.response.SanPhamWebResponse;
import com.example.style_store_be.entity.*;
import com.example.style_store_be.service.SanPhamWebService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/website/san-pham")
public class SanPhamWebController {
    private final SanPhamWebService sanPhamWebService;

    public SanPhamWebController(SanPhamWebService sanPhamWebService) {
        this.sanPhamWebService = sanPhamWebService;
    }

    @GetMapping("/hien-thi")
    Page<SanPhamWebDto> getPageChiTietSanPham(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) String tenSanPham,
            @RequestParam(required = false) Long thuongHieuId,
            @RequestParam(required = false) Long mauSacId,
            @RequestParam(required = false) Long chatLieuId,
            @RequestParam(required = false) Long kichThuocId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false, defaultValue = "") String sortOrder,
            @RequestParam(required = false) Long sanPhamId
            ) {
        Pageable pageable;
        if ("asc".equalsIgnoreCase(sortOrder)) {
            pageable = PageRequest.of(page, size, Sort.by("giaBan").ascending());
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            pageable = PageRequest.of(page, size, Sort.by("giaBan").descending());
        } else {
            pageable = PageRequest.of(page, size);
        }
        return sanPhamWebService.getPageChiTietSanPham(tenSanPham, thuongHieuId, mauSacId, chatLieuId, kichThuocId, minPrice, maxPrice,sanPhamId, pageable);
    }

    @GetMapping("/danh-sach-san-pham")
    List<SanPham> getListSanPham(){
        return sanPhamWebService.getListSanPham();
    }

    @GetMapping("/danh-sach-thuong-hieu")
    List<ThuongHieu> getListThuongHieu(){
        return sanPhamWebService.getListThuongHieu();
    }
    @GetMapping("/danh-sach-mau-sac")
    List<MauSacSp> getListMauSac(){
        return sanPhamWebService.getListMauSac();
    }
    @GetMapping("/danh-sach-kick-co")
    List<KichThuoc> getListKichCo(){
        return sanPhamWebService.getListKichThuoc();
    }
    @GetMapping("/danh-sach-chat-lieu")
    List<ChatLieu> getListChatLieu(){
        return sanPhamWebService.getListChatLieu();
    }
    @GetMapping("/danh-sach-xuat-xu")
    List<XuatXu> getListXuatXu(){
        return sanPhamWebService.getListXuatXu();
    }
    @GetMapping("/danh-sach-hinh-anh")
    List<HinhAnh> getListHinhAnh(){
        return sanPhamWebService.getListHinhAnh();
    }

    @GetMapping("/chi-tiet-san-pham/{id}")
    public SanPhamWebResponse detailSanPhamCt(@PathVariable("id") Long id){
        return sanPhamWebService.detailSanPhamCt(id);
    }

    @GetMapping("/page-san-pham")
    public Page<SanPham> getPageSanPham(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ngayTao").descending());
        return sanPhamWebService.getPageSanPham(pageable);
    }

    @GetMapping("/hien-thi-san-pham-giam-gia")
    public List<SanPhamWebDto> getListGiamGiaChiTietSanPham(
            @RequestParam(required = false) String tenSanPham,
            @RequestParam(required = false) Long thuongHieuId,
            @RequestParam(required = false) Long mauSacId,
            @RequestParam(required = false) Long chatLieuId,
            @RequestParam(required = false) Long kichThuocId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false, defaultValue = "") String sortOrder,
            @RequestParam(required = false) Long sanPhamId
    ) {
        Sort sort = Sort.unsorted();
        if ("asc".equalsIgnoreCase(sortOrder)) {
            sort = Sort.by("giaBan").ascending();
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            sort = Sort.by("giaBan").descending();
        }

        return sanPhamWebService.getListChiTietSanPham(
                tenSanPham, thuongHieuId, mauSacId, chatLieuId, kichThuocId,
                minPrice, maxPrice, sort, sanPhamId
        );
    }

    @GetMapping("/hien-thi-san-pham-admin/{sanPhamId}")
    public Page<SanPhamWebDto> getPageChiTietSanPhamAdmin(
            @PathVariable("sanPhamId") Long sanPhamId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) String tenSanPham,
            @RequestParam(required = false) Long thuongHieuId,
            @RequestParam(required = false) Long mauSacId,
            @RequestParam(required = false) Long chatLieuId,
            @RequestParam(required = false) Long kichThuocId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false, defaultValue = "") String sortOrder
    ) {
        Pageable pageable;
        if ("asc".equalsIgnoreCase(sortOrder)) {
            pageable = PageRequest.of(page, size, Sort.by("giaBan").ascending());
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            pageable = PageRequest.of(page, size, Sort.by("giaBan").descending());
        } else {
            pageable = PageRequest.of(page, size);
        }
        return sanPhamWebService.getPageChiTietSanPhamBySanPhamIdAndFilters(
                tenSanPham, thuongHieuId, mauSacId, chatLieuId, kichThuocId,
                minPrice, maxPrice, sanPhamId, pageable
        );
    }

    @PostMapping("/them-san-pham-chi-tiet")
    public ApiResponse<ChiTietSanPham> addSanPhamHoaDon (@RequestBody SanPhamAdminCrRequest request){
        ApiResponse<ChiTietSanPham> apiResponse = new ApiResponse<>();
        apiResponse.setResult(sanPhamWebService.addSanPhamChiTiet(request));
        return apiResponse;
    }


    @GetMapping("/chi-tiet-san-pham-admin/{id}")
    public SanPhamAdminResponse detailSanPhamCtAdmin(@PathVariable("id") Long id){
        return sanPhamWebService.detailSanPhamCtAdmin(id);
    }

    @PutMapping("/cap-nhat-thong-tin-san-pham-chi-tiet/{id}")
    String updateSanPhamCTAdmin(@PathVariable Long id, @RequestBody SanPhamAdminUpdateReq request) {
        return sanPhamWebService.updateSanPhamCTAdmin(id,request);
    }

    @GetMapping("/hien-thi-san-pham-admin2/{sanPhamId}")
    public Page<SanPhamWebDto> getPageChiTietSanPhamAdmin3(
            @PathVariable("sanPhamId") Long sanPhamId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) String tenSanPham,
            @RequestParam(required = false) Long thuongHieuId,
            @RequestParam(required = false) Long mauSacId,
            @RequestParam(required = false) Long chatLieuId,
            @RequestParam(required = false) Long kichThuocId,
            @RequestParam(required = false) Long xuatXuId, // ✅ thêm vào request
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false, defaultValue = "") String sortOrder
    ) {
        Pageable pageable;
        if ("asc".equalsIgnoreCase(sortOrder)) {
            pageable = PageRequest.of(page, size, Sort.by("giaBan").ascending());
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            pageable = PageRequest.of(page, size, Sort.by("giaBan").descending());
        } else {
            pageable = PageRequest.of(page, size);
        }

        return sanPhamWebService.getPageChiTietSanPhamBySanPhamIdAndFilters2(
                tenSanPham, thuongHieuId, mauSacId, chatLieuId, kichThuocId,
                xuatXuId,
                minPrice, maxPrice, sanPhamId, pageable
        );
    }

    @GetMapping("/chuyen-trang-thai-spct/{id}")
    public String chuyenTrangThaiSPCT(@PathVariable Long id) {
        sanPhamWebService.chuyenTrangThaiSPCT(id);
        return "Chuyển trạng thái đơn hàng thành công";
    }
    @GetMapping("/chuyen-trang-thai-sp/{id}")
    public String chuyenTrangThaiSP(@PathVariable Long id) {
        sanPhamWebService.chuyenTrangThaiSP(id);
        return "Chuyển trạng thái sp thành công";
    }


}
