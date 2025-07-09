package com.example.style_store_be_adminSell.service.Impl;

import com.example.style_store_be_adminSP.entity.SanPhamAdm;
import com.example.style_store_be_adminSell.dto.HoaDonSAdmDto;
import com.example.style_store_be_adminSell.entity.HoaDonSAdm;
import com.example.style_store_be_adminSell.entity.NguoiDungSAdm;
import com.example.style_store_be_adminSell.repository.HoaDonSAdmRepo;
import com.example.style_store_be_adminSell.repository.NguoiDungSAdmRepo;
import com.example.style_store_be_adminSell.repository.PtThanhToanSAdmRepo;
import com.example.style_store_be_adminSell.service.HoaDonSAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class HoaDonSAdmServiceImpl implements HoaDonSAdmService {
    @Autowired
    private HoaDonSAdmRepo hoaDonSAdmRepo;

    @Autowired
    private NguoiDungSAdmRepo nguoiDungRepository;

    @Autowired
    private PtThanhToanSAdmRepo ptThanhToanSAdmRepo;

    private HoaDonSAdm mapToEntity(HoaDonSAdmDto dto) {
        HoaDonSAdm entity = new HoaDonSAdm();
        entity.setId(dto.getId());

        if (dto.getNguoiTaoId() != null) {
            entity.setNguoiTao(nguoiDungRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new IllegalArgumentException("Người tạo không tồn tại")));
        }

        if (dto.getNguoiXuatId() != null) {
            entity.setNguoiXuat(nguoiDungRepository.findById(dto.getNguoiXuatId())
                    .orElse(null));
        }

        if (dto.getKhachHangId() != null) {
            entity.setKhachHang(nguoiDungRepository.findById(dto.getKhachHangId())
                    .orElse(null));
        }

        if (dto.getPtThanhToanId() != null) {
            entity.setThanhToan(ptThanhToanSAdmRepo.findById(dto.getPtThanhToanId())
                    .orElse(null));
        }

        entity.setMa(dto.getMa());
        entity.setNguoiDatHang(dto.getNguoiDatHang());
        entity.setNguoiNhanHang(dto.getNguoiNhanHang());
        entity.setDiaChiNhanHang(dto.getDiaChiNhanHang());
        entity.setTongSoLuongSp(dto.getTongSoLuongSp());
        entity.setTongTien(dto.getTongTien());
        entity.setTienThue(dto.getTienThue());
        entity.setNgayDat(dto.getNgayDat());
        entity.setNgayNhan(dto.getNgayNhan());
        entity.setNgayTao(dto.getNgayTao());
        entity.setNgaySua(dto.getNgaySua());
        entity.setNgayXoa(dto.getNgayXoa());
        entity.setTrangThai(dto.getTrangThai());
        entity.setMoTa(dto.getMoTa());

        return entity;
    }

    private HoaDonSAdmDto mapToDTO(HoaDonSAdm entity) {
        HoaDonSAdmDto dto = new HoaDonSAdmDto();
        dto.setId(entity.getId());
        if (entity.getNguoiTao() != null) {
            dto.setNguoiTaoId(entity.getNguoiTao().getId());
            dto.setTenNguoiTao(entity.getNguoiTao().getHoTen());
        }

        if (entity.getNguoiXuat() != null) {
            dto.setNguoiXuatId(entity.getNguoiXuat().getId());
            dto.setTenNguoiXuat(entity.getNguoiXuat().getHoTen());
        }

        if (entity.getThanhToan() != null) {
            dto.setPtThanhToanId(entity.getThanhToan().getId());
            dto.setTenPTThanhToan(entity.getThanhToan().getTen());
        }

        if (entity.getKhachHang() != null) {
            dto.setKhachHangId(entity.getKhachHang().getId());
            dto.setTenkhachHang(entity.getKhachHang().getHoTen());
        }
        dto.setMa(entity.getMa());
        dto.setNguoiDatHang(entity.getNguoiDatHang());
        dto.setNguoiNhanHang(entity.getNguoiNhanHang());
        dto.setDiaChiNhanHang(entity.getDiaChiNhanHang());
        dto.setTongSoLuongSp(entity.getTongSoLuongSp());
        dto.setTongTien(entity.getTongTien());
        dto.setTienThue(entity.getTienThue());
        dto.setNgayDat(entity.getNgayDat());
        dto.setNgayNhan(entity.getNgayNhan());
        dto.setNgayTao(entity.getNgayTao());
        dto.setNgaySua(entity.getNgaySua());
        dto.setNgayXoa(entity.getNgayXoa());
        dto.setTrangThai(entity.getTrangThai());
        dto.setMoTa(entity.getMoTa());

        return dto;
    }

    private void validate(HoaDonSAdm hoaDon) {
        if (hoaDon.getNguoiTao() == null || !nguoiDungRepository.existsById(hoaDon.getNguoiTao().getId())) {
            throw new IllegalArgumentException("Người dùng không tồn tại");
        }
        if (hoaDon.getNguoiXuat() == null || !nguoiDungRepository.existsById(hoaDon.getNguoiXuat().getId())) {
            throw new IllegalArgumentException("Người xuất không tồn tại");
        }
        if (hoaDon.getKhachHang() == null || !nguoiDungRepository.existsById(hoaDon.getKhachHang().getId())) {
            throw new IllegalArgumentException("Khách hàng không tồn tại");
        }
        if (hoaDon.getThanhToan() == null || !ptThanhToanSAdmRepo.existsById(hoaDon.getThanhToan().getId())) {
            throw new IllegalArgumentException("Phương thức thanh toán không tồn tại");
        }

    }

    @Override
    public Page<HoaDonSAdmDto> findAll(Pageable pageable) {
        return hoaDonSAdmRepo.findAll(pageable).map(this::mapToDTO);
    }

    @Override
    public Page<HoaDonSAdmDto> findByTrangThai(Integer trangThai, Pageable pageable) {
        return hoaDonSAdmRepo.findByTrangThai(trangThai,pageable).map(this::mapToDTO);
    }

    @Override
    public HoaDonSAdm findOne(String ma) {
        return hoaDonSAdmRepo.findByMa(ma);
    }

    @Override
    public HoaDonSAdmDto addHoaDon(HoaDonSAdmDto hoaDon) {
        if (hoaDon.getNguoiTaoId() == null) {
            hoaDon.setNguoiTaoId(1L);// hoặc gán từ session nếu có
            hoaDon.setNguoiXuatId(1L);
        }

        long count = hoaDonSAdmRepo.count();
        String ma = String.format("HD%03d", count + 1);
        hoaDon.setMa(ma);

        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setNgayNhan(LocalDateTime.now());
        hoaDon.setNgayDat(LocalDateTime.now());
        hoaDon.setTongSoLuongSp(0);
        hoaDon.setTrangThai(0);

        HoaDonSAdm hd = mapToEntity(hoaDon);
        HoaDonSAdm saved = hoaDonSAdmRepo.save(hd);
        return mapToDTO(saved);
    }

    @Override
    public HoaDonSAdm findHoaDonById(Long id) {
        return hoaDonSAdmRepo.findById(id).orElse(null);
    }

    @Override
    public List<HoaDonSAdm> findByMonthsAndTrangThai(LocalDateTime fromDate) {
        List<HoaDonSAdm> list = hoaDonSAdmRepo.findHoaDonTrongThangVaTrangThai1(fromDate);
        return list.stream().toList();
    }

    @Override
    public List<HoaDonSAdm> findByDayAndTrangThai(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        List<HoaDonSAdm> list = hoaDonSAdmRepo.findHoaDonNgayBDVaNgayKTAdnTrangThai1(startOfDay,endOfDay);
        return list.stream().toList();
    }


}
