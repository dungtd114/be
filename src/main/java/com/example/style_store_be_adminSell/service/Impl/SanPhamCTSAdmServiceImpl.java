package com.example.style_store_be_adminSell.service.Impl;

import com.example.style_store_be_adminSP.dto.SanPhamCtDTOAdm;
import com.example.style_store_be_adminSP.entity.SanPhamCtAdm;
import com.example.style_store_be_adminSP.reposytory.SanPhamRepoAdm;
import com.example.style_store_be_adminSell.repository.SanPhamCTSAdmRepo;
import com.example.style_store_be_adminSell.service.SanPhamCTSAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class SanPhamCTSAdmServiceImpl implements SanPhamCTSAdmService {
    @Autowired
    private SanPhamCTSAdmRepo sanPhamCtRepository;

    @Autowired
    private SanPhamRepoAdm sanPhamRepository;


    @Override
    public Optional<SanPhamCtDTOAdm> findById(Long id) {
        return sanPhamCtRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public Optional<SanPhamCtDTOAdm> findByMa(String ma) {
        return sanPhamCtRepository.findByMa(ma).map(this::mapToDTO);
    }

    @Override
    public Page<SanPhamCtDTOAdm> searchBySanPhamTen(String ten, Pageable pageable) {
        return sanPhamCtRepository.findBySanPhamTenContaining(ten, pageable).map(this::mapToDTO);
    }

    @Override
    public Page<SanPhamCtDTOAdm> findByTrangThai(Integer trangThai, Pageable pageable) {
        return sanPhamCtRepository.findByTrangThai(trangThai, pageable).map(this::mapToDTO);
    }

    @Override
    public Page<SanPhamCtDTOAdm> findAll(Pageable pageable) {
        return sanPhamCtRepository.findAll(pageable).map(this::mapToDTO);
    }
    @Override
    public SanPhamCtDTOAdm updateSoLuongSanPhamCT(Long id, Integer soLuong) {
        SanPhamCtAdm existing = sanPhamCtRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm chi tiết không tồn tại"));
        existing.setSoLuong(soLuong);
        SanPhamCtAdm saved = sanPhamCtRepository.save(existing);
        return mapToDTO(saved);
    }

    @Override
    public Page<SanPhamCtDTOAdm> filterByAttributes(
            Long sanPhamId,
            String sanPhamMa,
            String sanPhamTen,
            Long mauSacId,
            Long thuongHieuId,
            Long kichThuocId,
            Long xuatXuId,
            Long chatLieuId,
            Pageable pageable) {

        Page<SanPhamCtAdm> result = sanPhamCtRepository.findByAttributes(
                sanPhamId,
                sanPhamMa,
                sanPhamTen,
                mauSacId,
                thuongHieuId,
                kichThuocId,
                xuatXuId,
                chatLieuId,
                pageable
        );

        return result.map(this::mapToDTO);
    }


    @Override
    public Page<SanPhamCtDTOAdm> searchBySanPhamMa(String ma, Pageable pageable) {
        return sanPhamCtRepository.findBySanPhamMaContaining(ma, pageable).map(this::mapToDTO);
    }

    private SanPhamCtDTOAdm mapToDTO(SanPhamCtAdm entity) {
        SanPhamCtDTOAdm dto = new SanPhamCtDTOAdm();
        dto.setId(entity.getId());
        dto.setHinhAnhMauSacId(entity.getHinhAnhMauSac() != null ? entity.getHinhAnhMauSac().getId() : null);
        dto.setSanPhamId(entity.getSanPham().getId());
        dto.setMauSacId(entity.getMauSac().getId());
        dto.setThuongHieuId(entity.getThuongHieu().getId());
        dto.setKichThuocId(entity.getKichThuoc().getId());
        dto.setXuatXuId(entity.getXuatXu().getId());
        dto.setChatLieuId(entity.getChatLieu().getId());
        dto.setMa(entity.getMa());
        dto.setGiaNhap(entity.getGiaNhap());
        dto.setGiaBan(entity.getGiaBan());
        dto.setGiaBanGoc(entity.getGiaBanGoc());
        dto.setSoLuong(entity.getSoLuong());
        dto.setMoTa(entity.getMoTa());
        dto.setTrangThai(entity.getTrangThai());
        dto.setNgayTao(entity.getNgayTao());
        dto.setNgaySua(entity.getNgaySua());
        dto.setNgayXoa(entity.getNgayXoa());
        // Điền thông tin bổ sung
        dto.setTenSanPham(entity.getSanPham().getTen());
        dto.setTenMauSac(entity.getMauSac().getTen());
        dto.setTenThuongHieu(entity.getThuongHieu().getTen());
        dto.setTenKichThuoc(entity.getKichThuoc().getTen());
        dto.setTenXuatXu(entity.getXuatXu().getTen());
        dto.setTenChatLieu(entity.getChatLieu().getTen());
        dto.setUrlHinhAnhMauSac(entity.getHinhAnhMauSac() != null ? entity.getHinhAnhMauSac().getHinhAnh() : null);
        return dto;
    }

}
