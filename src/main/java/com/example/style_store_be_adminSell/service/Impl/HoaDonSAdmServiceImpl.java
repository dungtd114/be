package com.example.style_store_be_adminSell.service.Impl;


import com.example.style_store_be_adminSell.dto.HoaDonSAdmDto;
import com.example.style_store_be_adminSell.entity.DiaChiNhanSAdm;
import com.example.style_store_be_adminSell.entity.HoaDonSAdm;
import com.example.style_store_be_adminSell.entity.NguoiDungSAdm;
import com.example.style_store_be_adminSell.entity.PtThanhToanSAdm;
import com.example.style_store_be_adminSell.repository.DiaChiNhanSAdmRepo;
import com.example.style_store_be_adminSell.repository.HoaDonSAdmRepo;
import com.example.style_store_be_adminSell.repository.NguoiDungSAdmRepo;
import com.example.style_store_be_adminSell.repository.PtThanhToanSAdmRepo;
import com.example.style_store_be_adminSell.service.HoaDonSAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class HoaDonSAdmServiceImpl implements HoaDonSAdmService {
    @Autowired
    private HoaDonSAdmRepo hoaDonSAdmRepo;

    @Autowired
    private NguoiDungSAdmRepo nguoiDungRepository;

    @Autowired
    private PtThanhToanSAdmRepo ptThanhToanSAdmRepo;

    @Autowired
    private DiaChiNhanSAdmRepo diaChiNhanSAdmRepo;

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
    @PreAuthorize("hasAuthority('CREATE_ORDER')")
    public Page<HoaDonSAdmDto> findByTrangThai(Integer trangThai, Pageable pageable) {
        return hoaDonSAdmRepo.findByTrangThai(trangThai,pageable).map(this::mapToDTO);
    }

    @Override
    public HoaDonSAdm findOne(String ma) {
        return hoaDonSAdmRepo.findByMa(ma);
    }

    @Override
    @PreAuthorize("hasAuthority('CREATE_ORDER')")
    public HoaDonSAdmDto addHoaDon(HoaDonSAdmDto hoaDon) {
        if (hoaDon.getNguoiTaoId() == null) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            NguoiDungSAdm user = nguoiDungRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
            hoaDon.setNguoiTaoId(user.getId());// hoặc gán từ session nếu có
        }

        long count = hoaDonSAdmRepo.count();
        String ma = String.format("HD%03d", count + 1);
        hoaDon.setMa(ma);

        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setNgayNhan(LocalDateTime.now());
        hoaDon.setNgayDat(LocalDateTime.now());
        hoaDon.setTongSoLuongSp(0);
        hoaDon.setTrangThai(6);

        HoaDonSAdm hd = mapToEntity(hoaDon);
        HoaDonSAdm saved = hoaDonSAdmRepo.save(hd);
        return mapToDTO(saved);
    }

    @Override
    public HoaDonSAdm findHoaDonById(Long id) {
        return hoaDonSAdmRepo.findById(id).orElse(null);
    }

    @Override
    public int deleteHoaDon(Long id) {
        return hoaDonSAdmRepo.updateHoaDonTrangThai(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<HoaDonSAdm> findByMonthsAndTrangThai(LocalDateTime fromDate) {
        List<HoaDonSAdm> list = hoaDonSAdmRepo.findHoaDonTrongThangVaTrangThai1(fromDate);
        return list.stream().toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<HoaDonSAdm> findByDayAndTrangThai(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        List<HoaDonSAdm> list = hoaDonSAdmRepo.findHoaDonNgayBDVaNgayKTAdnTrangThai1(startOfDay,endOfDay);
        return list.stream().toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<HoaDonSAdm> findByDay(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        List<HoaDonSAdm> list = hoaDonSAdmRepo.findHoaDonNgayBDVaNgayKT(startOfDay,endOfDay);
        return list.stream().toList();
    }

    public String updateKhachHangChoHoaDon(Long hoaDonId, HoaDonSAdmDto hoaDonSAdmDto) {
        if (hoaDonSAdmDto == null || hoaDonSAdmDto.getKhachHangId() == null) {
            throw new IllegalArgumentException("Thiếu thông tin khách hàng");
        }

        HoaDonSAdm hoaDon = hoaDonSAdmRepo.findById(hoaDonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy hoá đơn"));

        NguoiDungSAdm khachHang = nguoiDungRepository.findById(hoaDonSAdmDto.getKhachHangId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy khách hàng"));

        hoaDon.setKhachHang(khachHang);
        hoaDon.setNguoiDatHang(khachHang.getHoTen());

        Integer hinhThucNhan = hoaDonSAdmDto.getHinhThucNhanHang();
        Long diaChiNhanId = hoaDonSAdmDto.getDiaChiNhanId();

        if (hinhThucNhan != null && hinhThucNhan == 0) {
            hoaDon.setNguoiNhanHang(khachHang.getHoTen());
            hoaDon.setDiaChiNhanHang("Tại cửa hàng");
        } else {
            DiaChiNhanSAdm diaChiNhan = null;
            if (diaChiNhanId != null) {
                diaChiNhan = diaChiNhanSAdmRepo.findById(diaChiNhanId).orElse(null);
            }

            if (diaChiNhan != null && diaChiNhan.getNguoiDungSAdm().getId().equals(khachHang.getId())) {
                hoaDon.setNguoiNhanHang(
                        diaChiNhan.getTenNguoiNhan() != null ? diaChiNhan.getTenNguoiNhan() : khachHang.getHoTen()
                );

                String diaChiDayDu = Stream.of(
                        diaChiNhan.getSoNha(),
                        diaChiNhan.getXa(),
                        diaChiNhan.getHuyen(),
                        diaChiNhan.getTinh()
                ).filter(Objects::nonNull).collect(Collectors.joining(", "));

                hoaDon.setDiaChiNhanHang(diaChiDayDu);
            } else {
                hoaDon.setNguoiNhanHang(khachHang.getHoTen());
                hoaDon.setDiaChiNhanHang(khachHang.getDiaChi());
            }
        }

        hoaDonSAdmRepo.save(hoaDon);
        return "Cập nhật khách hàng thành công";
    }

    public String capNhatThanhToanVaThongTinHoaDon(Long hoaDonId, HoaDonSAdmDto hoaDonSAdmDto) {
        if (hoaDonSAdmDto == null || hoaDonSAdmDto.getPtThanhToanId() == null) {
            throw new IllegalArgumentException("Thiếu thông tin thanh toán");
        }

        HoaDonSAdm hoaDon = hoaDonSAdmRepo.findById(hoaDonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy hoá đơn"));

        PtThanhToanSAdm pttt = ptThanhToanSAdmRepo.findById(hoaDonSAdmDto.getPtThanhToanId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy phương thức thanh toán"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        NguoiDungSAdm user = nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        // Cập nhật các thông tin hoá đơn
        hoaDon.setNguoiXuat(user); // Nếu có user hiện tại, nên thay thế bằng auth
        hoaDon.setThanhToan(pttt);
        hoaDon.setTongSoLuongSp(hoaDonSAdmDto.getTongSoLuongSp());
        hoaDon.setTongTien(hoaDonSAdmDto.getTongTien());
        hoaDon.setTrangThai(hoaDonSAdmDto.getTrangThai()); // Có thể dùng enum hoặc hằng số để rõ nghĩa hơn
        hoaDon.setMoTa(hoaDonSAdmDto.getMoTa());

        // Gán tiền thuế theo hình thức nhận
        if (Objects.equals(hoaDonSAdmDto.getHinhThucNhanHang(), 3)) {
            hoaDon.setTienThue(BigDecimal.valueOf(0));
        } else {
            hoaDon.setTienThue(BigDecimal.valueOf(30000));
        }

        hoaDonSAdmRepo.save(hoaDon);
        return "Cập nhật hoá đơn thành công";
    }


}
