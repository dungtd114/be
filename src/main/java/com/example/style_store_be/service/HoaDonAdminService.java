package com.example.style_store_be.service;

import com.example.style_store_be.dto.HoaDonAdminDto;
import com.example.style_store_be.dto.LichSuHoaDonDto;
import com.example.style_store_be.dto.request.HoaDonUpdateRequest;
import com.example.style_store_be.dto.response.HoaDonAdminResponse;
import com.example.style_store_be.dto.response.HoaDonUDResponse;
import com.example.style_store_be.dto.response.SanPhamHoaDonAdminResponse;
import com.example.style_store_be.entity.ChiTietSanPham;
import com.example.style_store_be.entity.HoaDon;
import com.example.style_store_be.entity.HoaDonCt;
import com.example.style_store_be.entity.LichSuHoaDon;
import com.example.style_store_be.mapper.DonHangMapper;
import com.example.style_store_be.repository.LichSuDonHangRepo;
import com.example.style_store_be.repository.SanPhamWebRepo;
import com.example.style_store_be.repository.website.DonHangChiTietRepo;
import com.example.style_store_be.repository.website.DonHangRepoSitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class HoaDonAdminService {
    DonHangRepoSitory donHangRepoSitory;
    SanPhamWebRepo sanPhamWebRepo;
    DonHangChiTietRepo donHangChiTietRepo;
    DonHangMapper donHangMapper;
    LichSuDonHangRepo lichSuDonHangRepo;
    public Page<HoaDonAdminDto> getPageHoaDonAdmin(String maHoaDonOrTenKhachHang0rSoDienThoai, Integer trangThaiDonHang, Integer trangThaiThanhToan, Integer phuongThucThanhToan, Pageable pageable) {
        return donHangRepoSitory.getPageHoaDonAdmin(maHoaDonOrTenKhachHang0rSoDienThoai, trangThaiDonHang, trangThaiThanhToan, phuongThucThanhToan, pageable);
    }


    public HoaDonAdminResponse getHoaDonAdminDetail(Long id) {
        HoaDonAdminResponse hoaDonResponse = donHangRepoSitory.findHoaDonAdminResponseById(id)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        List<SanPhamHoaDonAdminResponse> sanPhamResponses =
                donHangRepoSitory.findSanPhamByHoaDonId(id);

        hoaDonResponse.setSanPhams(sanPhamResponses);

        return hoaDonResponse;
    }

    public HoaDonUDResponse getHoaDonUDDetail(Long id) {
        HoaDon hoaDon = donHangRepoSitory.findById(id).orElseThrow(()-> new RuntimeException("Hóa đơn không tồn tại"));
        return donHangMapper.toHoaDonUDResponse(hoaDon);
    }

    public HoaDonUDResponse updateHoaDonUDDetail(Long id, HoaDonUpdateRequest request) {
        HoaDon hoaDon = donHangRepoSitory.findById(id).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        donHangMapper.hoaDonUdateRequest(hoaDon, request);
        hoaDon.setNgaySua(new Date());
        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setHoaDon(hoaDon);
        lichSuHoaDon.setNgayCapNhat(new Date());
        lichSuHoaDon.setTieuDe("Cập nhật thông tin đơn hàng");
        lichSuHoaDon.setNoiDung("Thông tin đơn hàng đã được cập nhật");
        lichSuHoaDon.setNguoiThucHien("Admin");
        donHangRepoSitory.save(hoaDon);
        return donHangMapper.toHoaDonUDResponse(hoaDon);
    }

    @Transactional
    public void chuyenTrangThaiDonHang(Long id) {
        // Lấy thông tin hóa đơn
        HoaDon hoaDon = donHangRepoSitory.findById(id)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        Integer currentTrangThai = hoaDon.getTrangThai();
        int nextTrangThai = currentTrangThai < 6 ? currentTrangThai + 1 : currentTrangThai;

        // Nếu chuyển sang trạng thái xác nhận (giả sử trạng thái 2 là xác nhận)
        if (nextTrangThai == 1) {
            // Lấy tất cả chi tiết hóa đơn
            List<HoaDonCt> chiTietHoaDons = donHangChiTietRepo.findByHoaDonId(id);

            for (HoaDonCt chiTiet : chiTietHoaDons) {
                // Lấy sản phẩm chi tiết tương ứng
                ChiTietSanPham sanPhamChiTiet = chiTiet.getSanPhamCt();

                // Kiểm tra số lượng tồn kho
                if (sanPhamChiTiet.getSoLuong() < chiTiet.getSoLuong()) {
                    throw new RuntimeException("Số lượng sản phẩm " + sanPhamChiTiet.getId() + " không đủ");
                }

                // Trừ số lượng tồn kho
                int soLuongConLai = sanPhamChiTiet.getSoLuong() - chiTiet.getSoLuong();
                sanPhamChiTiet.setSoLuong(soLuongConLai);

                // Cập nhật sản phẩm chi tiết
                sanPhamWebRepo.save(sanPhamChiTiet);
            }
        }
        if (nextTrangThai ==3){
            hoaDon.setTrangThaiThanhToan(1);
            hoaDon.setTienKhachTra(hoaDon.getTongTien()+hoaDon.getTienThue());
            hoaDon.setNgayNhan(new Date());
        }


        // Cập nhật trạng thái hóa đơn
        donHangRepoSitory.updateTrangThaiHoaDon(id, nextTrangThai);

        // Lưu lịch sử hóa đơn
        LichSuHoaDon lichSu = new LichSuHoaDon();
        HoaDon hoaDonRef = new HoaDon();
        hoaDonRef.setId(id);

        lichSu.setHoaDon(hoaDonRef);
        lichSu.setTieuDe("" + nextTrangThai);
        lichSu.setNoiDung("" + nextTrangThai);
        lichSu.setNgayCapNhat(new Date());
        lichSu.setNguoiThucHien("hoa");

        lichSuDonHangRepo.save(lichSu);
    }

    public List<LichSuHoaDonDto> getListLichSu(Long id) {
        return lichSuDonHangRepo.findAllByHoaDonIdOrderByNgayCapNhatDesc(id);
    }
}
