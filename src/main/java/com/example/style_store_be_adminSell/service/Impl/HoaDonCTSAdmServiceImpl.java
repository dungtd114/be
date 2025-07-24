package com.example.style_store_be_adminSell.service.Impl;

import com.example.style_store_be_adminSP.entity.SanPhamCtAdm;
import com.example.style_store_be_adminSell.dto.HoaDonCTSAdmDto;
import com.example.style_store_be_adminSell.entity.HoaDonCTSAdm;
import com.example.style_store_be_adminSell.entity.HoaDonSAdm;
import com.example.style_store_be_adminSell.repository.HoaDonSAdmRepo;
import com.example.style_store_be_adminSell.repository.HoaDonSCtAdmRepo;
import com.example.style_store_be_adminSell.repository.SanPhamCTSAdmRepo;
import com.example.style_store_be_adminSell.service.HoaDonCTSAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HoaDonCTSAdmServiceImpl implements HoaDonCTSAdmService {

    @Autowired
    private HoaDonSCtAdmRepo hoaDonSCtAdmRepo;

    @Autowired
    private HoaDonSAdmRepo hoaDonSAdmRepo;

    @Autowired
    private SanPhamCTSAdmRepo sanPhamCTSAdmRepo;

    private HoaDonCTSAdm mapToEntity(HoaDonCTSAdmDto dto) {
        HoaDonSAdm hoaDon = hoaDonSAdmRepo.findById(dto.getHoaDonId())
                .orElseThrow(() -> new IllegalArgumentException("Hoá đơn k tồn tại"));

        HoaDonCTSAdm entity = new HoaDonCTSAdm();
        entity.setId(dto.getId());
        entity.setHoaDon(hoaDon);
        if (dto.getSanPhamCTId() != null) {
            entity.setSanPhamCt(sanPhamCTSAdmRepo.findById(dto.getSanPhamCTId())
                    .orElse(null));

            entity.setNgayTao(sanPhamCTSAdmRepo.findById(dto.getSanPhamCTId()).orElseThrow().getNgayTao());
            entity.setNgaySua(sanPhamCTSAdmRepo.findById(dto.getSanPhamCTId()).orElseThrow().getNgaySua());
            entity.setNgayXoa(sanPhamCTSAdmRepo.findById(dto.getSanPhamCTId()).orElseThrow().getNgayXoa());
            entity.setTrangThai(sanPhamCTSAdmRepo.findById(dto.getSanPhamCTId()).orElseThrow().getTrangThai());
        }
        entity.setTenSanPham(dto.getTenSanPham());
        entity.setGiaTien(dto.getGiaTien());
        entity.setSoLuong(dto.getSoLuong());
        entity.setThanhTien(dto.getThanhTien());

        return entity;
    }

    private HoaDonCTSAdmDto mapToDTO(HoaDonCTSAdm entity) {
        HoaDonCTSAdmDto dto = new HoaDonCTSAdmDto();
        dto.setId(entity.getId());
        if (entity.getHoaDon() != null) {
            dto.setHoaDonId(entity.getHoaDon().getId());
        }

        if (entity.getSanPhamCt() != null) {
            dto.setSanPhamCTId(entity.getSanPhamCt().getId());
            dto.setMaSanPhamCtAdm(entity.getSanPhamCt().getMa());
            dto.setTenSanPham(entity.getTenSanPham());
            dto.setGiaGocSP(entity.getSanPhamCt().getGiaBanGoc());
        }

        dto.setGiaTien(entity.getGiaTien());
        dto.setSoLuong(entity.getSoLuong());
        dto.setThanhTien(entity.getThanhTien());
        dto.setNgayTao(entity.getNgayTao());
        dto.setNgaySua(entity.getNgaySua());
        dto.setNgayXoa(entity.getNgayXoa());

        dto.setTrangThai(entity.getTrangThai());
        return dto;
    }

    private void validate(HoaDonCTSAdm hoaDon) {
        if (hoaDon.getSanPhamCt() == null || !sanPhamCTSAdmRepo.existsById(hoaDon.getSanPhamCt().getId())) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại");
        }
        if (hoaDon.getHoaDon() == null || !hoaDonSAdmRepo.existsById(hoaDon.getHoaDon().getId())) {
            throw new IllegalArgumentException("Hoá đơn không tồn tại");
        }
    }

    @Override
    public Page<HoaDonCTSAdmDto> getAllHoaDonCTS(Pageable pageable) {
        return hoaDonSCtAdmRepo.findAll(pageable).map(this::mapToDTO);
    }

    @Transactional
    @Override
    public HoaDonCTSAdmDto addHDCTS(HoaDonCTSAdmDto hoaDonCTSAdm) {
        SanPhamCtAdm chiTietSAdm = sanPhamCTSAdmRepo.findById(hoaDonCTSAdm.getSanPhamCTId())
                .orElseThrow(()-> new IllegalArgumentException("Sản pham k ton tai"));
        if (chiTietSAdm.getSoLuong()<hoaDonCTSAdm.getSoLuong()) {
            throw new IllegalArgumentException("Số lượng trong kho không đủ");
        }
        chiTietSAdm.setSoLuong(chiTietSAdm.getSoLuong()-hoaDonCTSAdm.getSoLuong());
        sanPhamCTSAdmRepo.save(chiTietSAdm);
        HoaDonCTSAdm hd = mapToEntity(hoaDonCTSAdm);
        validate(hd);
       HoaDonCTSAdm saved = hoaDonSCtAdmRepo.save(hd);
        return mapToDTO(saved);
    }

    @Override
    public Page<HoaDonCTSAdmDto> findByIdHoaDon(Long idHoaDon, Pageable pageable) {
        return hoaDonSCtAdmRepo.findByHoaDon_Id(idHoaDon, pageable).map(this::mapToDTO);
    }

    @Override
    public HoaDonCTSAdmDto updateHDCTS(Long id, HoaDonCTSAdmDto hoaDonCTSAdm) {
        HoaDonCTSAdm hd = hoaDonSCtAdmRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hoá đơn không tồn tại"));

        HoaDonCTSAdm update = mapToEntity(hoaDonCTSAdm);
        validate(update);

        SanPhamCtAdm sanPhamCtAdm = sanPhamCTSAdmRepo.findById(update.getSanPhamCt().getId())
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại"));

        int soLuongTrongDonHangCu = hd.getSoLuong(); // Số lượng cũ trong đơn
        int soLuongMoi = update.getSoLuong();        // Số lượng mới
        int chenhLech = soLuongMoi - soLuongTrongDonHangCu; // Ví dụ: từ 2 lên 5 => chênh lệch = +3

        if (chenhLech > 0 && sanPhamCtAdm.getSoLuong() < chenhLech) {
            throw new IllegalArgumentException("Không đủ số lượng kho");
        }

        // Cập nhật kho: nếu chênh lệch dương => trừ thêm; âm => trả lại kho
        sanPhamCtAdm.setSoLuong(sanPhamCtAdm.getSoLuong() - chenhLech);
        sanPhamCTSAdmRepo.save(sanPhamCtAdm);

        // Cập nhật lại hóa đơn chi tiết
        hd.setHoaDon(update.getHoaDon());
        hd.setSanPhamCt(update.getSanPhamCt());
        hd.setTenSanPham(update.getTenSanPham());
        hd.setGiaTien(update.getGiaTien());
        hd.setSoLuong(update.getSoLuong());
        hd.setThanhTien(update.getThanhTien());
        hd.setTrangThai(update.getTrangThai());

        HoaDonCTSAdm saved = hoaDonSCtAdmRepo.save(hd);
        return mapToDTO(saved);
    }


    @Override
    public void deleteHDCTS(Long id) {
        HoaDonCTSAdm hd = hoaDonSCtAdmRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("Hoá đơn k tồn tại"));
        SanPhamCtAdm sanPhamCtAdm = hd.getSanPhamCt();
        sanPhamCtAdm.setSoLuong(sanPhamCtAdm.getSoLuong()+ hd.getSoLuong());
        sanPhamCTSAdmRepo.save(sanPhamCtAdm);
        hoaDonSCtAdmRepo.delete(hd);
    }


}
