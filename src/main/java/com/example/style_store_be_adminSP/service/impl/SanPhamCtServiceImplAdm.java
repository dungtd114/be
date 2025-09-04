package com.example.style_store_be_adminSP.service.impl;

import com.example.style_store_be_adminSP.dto.SanPhamCtDTOAdm;
import com.example.style_store_be_adminSP.entity.HinhAnhMauSacAdm;
import com.example.style_store_be_adminSP.entity.SanPhamCtAdm;
import com.example.style_store_be_adminSP.reposytory.*;
import com.example.style_store_be_adminSP.service.SanPhamCtServiceAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SanPhamCtServiceImplAdm implements SanPhamCtServiceAdm {

    @Autowired
    private ChiTietSPRepoAdm sanPhamCtRepository;
    @Autowired
    private GiamGiaSanPhamCtAdmRepository giamGiaSanPhamCtAdmRepository;

    @Autowired
    private SanPhamRepoAdm sanPhamRepository;

    @Autowired
    private MauSacSPRepoAdm mauSacRepository;

    @Autowired
    private ThuongHieuRepoAdm thuongHieuRepository;

    @Autowired
    private KichThuocRepoAdm kichThuocRepository;

    @Autowired
    private XuatXuRepoAdm xuatXuRepository;

    @Autowired
    private ChatLieuRepoAdm chatLieuRepository;

    @Autowired
    private HinhAnhRepoAdm hinhAnhMauSacRepository;

    @Override
    public SanPhamCtDTOAdm addSanPhamCt(SanPhamCtDTOAdm sanPhamCtDTO) {
        SanPhamCtAdm sanPhamCt = mapToEntity(sanPhamCtDTO);
        validateSanPhamCt(sanPhamCt);

        Optional<SanPhamCtAdm> existing = sanPhamCtRepository
                .findBySanPhamIdAndMauSacIdAndThuongHieuIdAndKichThuocIdAndXuatXuIdAndChatLieuId(
                        sanPhamCt.getSanPham().getId(),
                        sanPhamCt.getMauSac().getId(),
                        sanPhamCt.getThuongHieu().getId(),
                        sanPhamCt.getKichThuoc().getId(),
                        sanPhamCt.getXuatXu().getId(),
                        sanPhamCt.getChatLieu().getId()
                );

        if (existing.isPresent()) {
            throw new IllegalArgumentException("Sản phẩm chi tiết đã tồn tại. Bạn có muốn cập nhật số lượng không?");
        }

        // xử lý hình ảnh màu sắc
        if (sanPhamCtDTO.getHinhAnhMauSacId() != null) {
            Optional<HinhAnhMauSacAdm> hinhAnh = hinhAnhMauSacRepository.findById(sanPhamCtDTO.getHinhAnhMauSacId());
            if (hinhAnh.isPresent() && hinhAnh.get().getTrangThai() == 1 && hinhAnh.get().getNgayXoa() == null) {
                if (hinhAnh.get().getMauSac().getId().equals(sanPhamCt.getMauSac().getId())) {
                    sanPhamCt.setHinhAnhMauSac(hinhAnh.get());
                } else {
                    throw new IllegalArgumentException("Hình ảnh màu sắc không thuộc màu sắc đã chọn");
                }
            } else {
                throw new IllegalArgumentException("Hình ảnh màu sắc không tồn tại hoặc không hoạt động");
            }
        } else {
            sanPhamCt.setHinhAnhMauSac(null);
        }

        // sinh mã duy nhất
        String ma = "SPCT-" + UUID.randomUUID().toString().substring(0, 8);
        while (sanPhamCtRepository.existsByMa(ma)) {
            ma = "SPCT-" + UUID.randomUUID().toString().substring(0, 8);
        }

        sanPhamCt.setMa(ma);
        sanPhamCt.setNgayTao(LocalDateTime.now());
        sanPhamCt.setTrangThai(1); // mặc định đang bán
        sanPhamCt.setGiaBan(sanPhamCt.getGiaBanGoc());

        SanPhamCtAdm saved = sanPhamCtRepository.save(sanPhamCt);
        return mapToDTO(saved);
    }

    @Override
    public SanPhamCtDTOAdm updateSanPhamCt(Long id, SanPhamCtDTOAdm sanPhamCtDTO) {
        SanPhamCtAdm existing = sanPhamCtRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm chi tiết không tồn tại"));

        // chỉ cho phép cập nhật nếu SPCT đang bán (1) hoặc ngưng bán (0)
        if (existing.getTrangThai() != 0 && existing.getTrangThai() != 1) {
            throw new IllegalStateException("Không thể cập nhật sản phẩm chi tiết có trạng thái không hợp lệ");
        }

        SanPhamCtAdm updated = mapToEntity(sanPhamCtDTO);
        validateSanPhamCt(updated);

        // xử lý hình ảnh màu sắc
        if (sanPhamCtDTO.getHinhAnhMauSacId() != null) {
            Optional<HinhAnhMauSacAdm> hinhAnh = hinhAnhMauSacRepository.findById(sanPhamCtDTO.getHinhAnhMauSacId());

            if (hinhAnh.isPresent()) {
                HinhAnhMauSacAdm hinhAnhFound = hinhAnh.get();

                if (hinhAnhFound.getTrangThai() != 1 || hinhAnhFound.getNgayXoa() != null) {
                    throw new IllegalArgumentException("Hình ảnh màu sắc không tồn tại hoặc không hoạt động");
                }

                if (!hinhAnhFound.getMauSac().getId().equals(updated.getMauSac().getId())) {
                    throw new IllegalArgumentException("Hình ảnh màu sắc không thuộc màu sắc đã chọn");
                }

                updated.setHinhAnhMauSac(hinhAnhFound);
            } else {
                throw new IllegalArgumentException("Hình ảnh màu sắc không tồn tại");
            }
        } else {
            updated.setHinhAnhMauSac(null);
        }

        // kiểm tra mã trùng
        if (!existing.getMa().equals(updated.getMa()) && sanPhamCtRepository.existsByMa(updated.getMa())) {
            throw new IllegalArgumentException("Mã sản phẩm chi tiết đã tồn tại");
        }

        // kiểm tra giảm giá
        boolean coGiamGia = giamGiaSanPhamCtAdmRepository
                .existsActiveBySanPhamCtId(existing.getId(), LocalDateTime.now());

        if (coGiamGia) {
            if (!updated.getGiaBanGoc().equals(existing.getGiaBanGoc())) {
                throw new IllegalStateException("Không thể cập nhật giá gốc khi sản phẩm đang trong đợt giảm giá");
            }
        } else {
            existing.setGiaBanGoc(updated.getGiaBanGoc());
            existing.setGiaBan(updated.getGiaBanGoc());
        }

        // cập nhật thông tin
        existing.setHinhAnhMauSac(updated.getHinhAnhMauSac());
        existing.setSanPham(updated.getSanPham());
        existing.setMauSac(updated.getMauSac());
        existing.setThuongHieu(updated.getThuongHieu());
        existing.setKichThuoc(updated.getKichThuoc());
        existing.setXuatXu(updated.getXuatXu());
        existing.setChatLieu(updated.getChatLieu());
        existing.setMa(updated.getMa());
        existing.setGiaNhap(updated.getGiaNhap());
        existing.setSoLuong(updated.getSoLuong());
        existing.setMoTa(updated.getMoTa());
        existing.setTrangThai(updated.getTrangThai());
        existing.setNgaySua(LocalDateTime.now());

        SanPhamCtAdm saved = sanPhamCtRepository.save(existing);
        return mapToDTO(saved);
    }

    @Override
    public void deleteSanPhamCt(Long id) {
        if (id == null) {
            throw new RuntimeException("ID không được để trống khi cập nhật trạng thái");
        }

        SanPhamCtAdm existing = sanPhamCtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm chi tiết với id: " + id));

        if (existing.getTrangThai() != 0 && existing.getTrangThai() != 1) {
            throw new IllegalStateException("Trạng thái sản phẩm không hợp lệ (chỉ cho phép 0 hoặc 1)");
        }

        existing.setTrangThai(existing.getTrangThai() == 1 ? 0 : 1);
        existing.setNgaySua(LocalDateTime.now());
        sanPhamCtRepository.save(existing);
    }

    @Override
    public Optional<SanPhamCtDTOAdm> findById(Long id) {
        return sanPhamCtRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public Optional<SanPhamCtDTOAdm> findByMa(String ma) {
        return sanPhamCtRepository.findByMa(ma).map(this::mapToDTO);
    }

    @Override
    public Page<SanPhamCtDTOAdm> searchBySanPhamMa(String ten, Pageable pageable) {
        return sanPhamCtRepository.findBySanPhamMaContaining(ten, pageable).map(this::mapToDTO);
    }

    @Override
    public Page<SanPhamCtDTOAdm> findByTrangThai(Integer trangThai, Pageable pageable) {
        return sanPhamCtRepository.findByTrangThai(trangThai, pageable).map(this::mapToDTO);
    }

    @Override
    public Page<SanPhamCtDTOAdm> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return sanPhamCtRepository.findAllByOrderByNgayTaoDesc(pageable).map(this::mapToDTO);
    }

    @Override
    public Page<SanPhamCtDTOAdm> findBySanPhamId(Long sanPhamId, Pageable pageable) {
        return sanPhamCtRepository.findBySanPhamIdOrderByNgayTaoDesc(sanPhamId, pageable).map(this::mapToDTO);
    }

    @Override
    public Page<SanPhamCtDTOAdm> findByMauSacId(Long mauSacId, Pageable pageable) {
        return sanPhamCtRepository.findByMauSacId(mauSacId, pageable).map(this::mapToDTO);
    }

    @Override
    public Page<SanPhamCtDTOAdm> filterByAttributes(Long sanPhamId, Long mauSacId, Long thuongHieuId,
                                                    Long kichThuocId, Long xuatXuId, Long chatLieuId, Pageable pageable) {
        return sanPhamCtRepository.findByAttributes(sanPhamId, mauSacId, thuongHieuId,
                kichThuocId, xuatXuId, chatLieuId, pageable).map(this::mapToDTO);
    }

    private void validateSanPhamCt(SanPhamCtAdm sanPhamCt) {
        if (sanPhamCt.getSanPham() == null || !sanPhamRepository.findById(sanPhamCt.getSanPham().getId())
                .filter(sp -> sp.getTrangThai() == 1).isPresent()) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại hoặc không đang hoạt động");
        }
        if (sanPhamCt.getMauSac() == null || !mauSacRepository.findById(sanPhamCt.getMauSac().getId())
                .filter(ms -> ms.getTrangThai() == 1).isPresent()) {
            throw new IllegalArgumentException("Màu sắc không tồn tại hoặc không hoạt động");
        }
        if (sanPhamCt.getThuongHieu() == null || !thuongHieuRepository.findById(sanPhamCt.getThuongHieu().getId())
                .filter(th -> th.getTrangThai() == 1).isPresent()) {
            throw new IllegalArgumentException("Thương hiệu không tồn tại hoặc không hoạt động");
        }
        if (sanPhamCt.getKichThuoc() == null || !kichThuocRepository.findById(sanPhamCt.getKichThuoc().getId())
                .filter(kt -> kt.getTrangThai() == 1).isPresent()) {
            throw new IllegalArgumentException("Kích thước không tồn tại hoặc không hoạt động");
        }
        if (sanPhamCt.getXuatXu() == null || !xuatXuRepository.findById(sanPhamCt.getXuatXu().getId())
                .filter(xx -> xx.getTrangThai() == 1).isPresent()) {
            throw new IllegalArgumentException("Xuất xứ không tồn tại hoặc không hoạt động");
        }
        if (sanPhamCt.getChatLieu() == null || !chatLieuRepository.findById(sanPhamCt.getChatLieu().getId())
                .filter(cl -> cl.getTrangThai() == 1).isPresent()) {
            throw new IllegalArgumentException("Chất liệu không tồn tại hoặc không hoạt động");
        }
        if (sanPhamCt.getGiaBanGoc() == null || sanPhamCt.getGiaBanGoc().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Giá bán gốc phải lớn hơn 0");
        }
        if (sanPhamCt.getSoLuong() == null || sanPhamCt.getSoLuong() < 0) {
            throw new IllegalArgumentException("Số lượng không được nhỏ hơn 0");
        }
    }

    private SanPhamCtAdm mapToEntity(SanPhamCtDTOAdm dto) {
        SanPhamCtAdm entity = new SanPhamCtAdm();
        entity.setId(dto.getId());
        if (dto.getHinhAnhMauSacId() != null) {
            entity.setHinhAnhMauSac(hinhAnhMauSacRepository.findById(dto.getHinhAnhMauSacId())
                    .filter(hams -> hams.getTrangThai() == 1)
                    .orElseThrow(() -> new IllegalArgumentException("Hình ảnh màu sắc không tồn tại hoặc không hoạt động")));
        }
        entity.setSanPham(sanPhamRepository.findById(dto.getSanPhamId())
                .filter(sp -> sp.getTrangThai() == 1)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại hoặc không hoạt động")));
        entity.setMauSac(mauSacRepository.findById(dto.getMauSacId())
                .filter(ms -> ms.getTrangThai() == 1)
                .orElseThrow(() -> new IllegalArgumentException("Màu sắc không tồn tại hoặc không hoạt động")));
        entity.setThuongHieu(thuongHieuRepository.findById(dto.getThuongHieuId())
                .filter(th -> th.getTrangThai() == 1)
                .orElseThrow(() -> new IllegalArgumentException("Thương hiệu không tồn tại hoặc không hoạt động")));
        entity.setKichThuoc(kichThuocRepository.findById(dto.getKichThuocId())
                .filter(kt -> kt.getTrangThai() == 1)
                .orElseThrow(() -> new IllegalArgumentException("Kích thước không tồn tại hoặc không hoạt động")));
        entity.setXuatXu(xuatXuRepository.findById(dto.getXuatXuId())
                .filter(xx -> xx.getTrangThai() == 1)
                .orElseThrow(() -> new IllegalArgumentException("Xuất xứ không tồn tại hoặc không hoạt động")));
        entity.setChatLieu(chatLieuRepository.findById(dto.getChatLieuId())
                .filter(cl -> cl.getTrangThai() == 1)
                .orElseThrow(() -> new IllegalArgumentException("Chất liệu không tồn tại hoặc không hoạt động")));
        entity.setMa(dto.getMa());
        entity.setGiaNhap(dto.getGiaNhap());
        entity.setGiaBanGoc(dto.getGiaBanGoc());
        entity.setGiaBan(dto.getGiaBan());
        entity.setSoLuong(dto.getSoLuong());
        entity.setMoTa(dto.getMoTa());
        entity.setTrangThai(dto.getTrangThai());
        entity.setNgayTao(dto.getNgayTao());
        entity.setNgaySua(dto.getNgaySua());
        entity.setNgayXoa(dto.getNgayXoa());
        return entity;
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
        dto.setGiaBanGoc(entity.getGiaBanGoc());
        dto.setGiaBan(entity.getGiaBan());
        dto.setSoLuong(entity.getSoLuong());
        dto.setMoTa(entity.getMoTa());
        dto.setTrangThai(entity.getTrangThai());
        dto.setNgayTao(entity.getNgayTao());
        dto.setNgaySua(entity.getNgaySua());
        dto.setNgayXoa(entity.getNgayXoa());
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
