package com.example.style_store_be_adminSP.service.impl;

import com.example.style_store_be_adminSP.entity.SanPhamAdm;
import com.example.style_store_be_adminSP.reposytory.ChiTietSPRepoAdm;
import com.example.style_store_be_adminSP.reposytory.SanPhamRepoAdm;
import com.example.style_store_be_adminSP.service.ICommonServiceAdm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SanPhamAdminServiceImplAdm implements ICommonServiceAdm<SanPhamAdm> {

    private static final Logger logger = LoggerFactory.getLogger(SanPhamAdminServiceImplAdm.class);

    @Autowired
    private SanPhamRepoAdm sanPhamAdminRepository;

    @Autowired
    private ChiTietSPRepoAdm sanPhamCtRepository;

    @Override
    public Page<SanPhamAdm> getAll(int page, int size) {
        logger.info("Lấy danh sách sản phẩm chưa xóa, trang: {}, kích thước: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return sanPhamAdminRepository.findAllActiveByOrderByNgayTaoDesc(pageable);
    }

    @Override
    public SanPhamAdm getOne(Long id) {
        logger.info("Lấy sản phẩm với ID: {}", id);
        return sanPhamAdminRepository.findById(id)
                .orElseThrow(() -> new SanPhamException("Không tìm thấy sản phẩm với ID: " + id));
    }

    @Override
    public SanPhamAdm add(SanPhamAdm object) {
        logger.info("Thêm sản phẩm mới: {}", object.getTen());
        validate(object);
        if (object.getMa() == null || object.getMa().trim().isEmpty()) {
            object.setMa("SP-" + UUID.randomUUID().toString().substring(0, 8));
        }
        Optional<SanPhamAdm> existingByMa = sanPhamAdminRepository.findByMa(object.getMa());
        if (existingByMa.isPresent()) {
            throw new SanPhamException("Mã sản phẩm " + object.getMa() + " đã tồn tại");
        }
        object.setTrangThai(1); // Mặc định là Đang Kinh Doanh
        object.setNgayTao(LocalDateTime.now());
        object.setNgaySua(null);
        object.setNgayXoa(null);
        return sanPhamAdminRepository.save(object);
    }

    @Override
    public SanPhamAdm update(SanPhamAdm object) {
        logger.info("Cập nhật sản phẩm với ID: {}", object.getId());
        if (object.getId() == null) {
            throw new SanPhamException("ID không được để trống khi cập nhật");
        }

        validate(object);

        SanPhamAdm existing = sanPhamAdminRepository.findById(object.getId())
                .orElseThrow(() -> new SanPhamException("Không tìm thấy sản phẩm với ID: " + object.getId()));

        Optional<SanPhamAdm> existingByMa = sanPhamAdminRepository.findByMa(object.getMa());
        if (existingByMa.isPresent() && !existingByMa.get().getId().equals(object.getId())) {
            throw new SanPhamException("Mã sản phẩm " + object.getMa() + " đã tồn tại");
        }

        existing.setTen(object.getTen());
        existing.setMa(object.getMa());

        if (object.getTrangThai() != null) {
            if (object.getTrangThai() == 1) {
                existing.setTrangThai(1);
                existing.setNgayXoa(null); // Khôi phục
            } else if (object.getTrangThai() == 2) {
                existing.setTrangThai(2);
                existing.setNgayXoa(LocalDateTime.now()); // Tạm ngưng => xóa mềm
            } else if (object.getTrangThai() == 0) {
                existing.setTrangThai(0); // Hết hàng
            } else {
                throw new SanPhamException("Trạng thái mới không hợp lệ, chỉ được phép là 1 (đang kinh doanh), 2 (tạm ngưng), hoặc 0 (hết hàng)");
            }
        }

        existing.setNgaySua(LocalDateTime.now());
        return sanPhamAdminRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        logger.info("Chuyển đổi trạng thái sản phẩm với ID: {}", id);
        if (id == null) {
            throw new SanPhamException("ID sản phẩm không được để trống khi chuyển đổi trạng thái");
        }

        SanPhamAdm existing = sanPhamAdminRepository.findById(id)
                .orElseThrow(() -> new SanPhamException("Không tìm thấy sản phẩm với ID: " + id));

        if (existing.getTrangThai() == 1) {
            existing.setTrangThai(2); // Tạm ngưng
            existing.setNgayXoa(LocalDateTime.now());

            // 👉 Đồng bộ SPCT: chuyển tất cả sang trạng thái 2 (ngưng bán)
            sanPhamCtRepository.updateTrangThaiBySanPham(existing.getId(), 2);
        } else if (existing.getTrangThai() == 2) {
            existing.setTrangThai(1); // Kinh doanh lại
            existing.setNgayXoa(null);

            // 👉 Đồng bộ SPCT: chuyển tất cả sang trạng thái 1 (bán lại)
            sanPhamCtRepository.updateTrangThaiBySanPham(existing.getId(), 1);
        }

        existing.setNgaySua(LocalDateTime.now());
        sanPhamAdminRepository.save(existing);
    }

    public Page<SanPhamAdm> getActive(int page, int size) {
        logger.info("Lấy danh sách sản phẩm hoạt động, trang: {}, kích thước: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return sanPhamAdminRepository.findByNgayXoaIsNull(pageable);
    }

    public Page<SanPhamAdm> getDeleted(int page, int size) {
        logger.info("Lấy danh sách sản phẩm đã xóa, trang: {}, kích thước: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return sanPhamAdminRepository.findByNgayXoaIsNotNull(pageable);
    }

    public Page<SanPhamWithQuantity> searchSanPhamWithTotalQuantity(String search, int page, int size) {
        logger.info("Tìm kiếm sản phẩm với từ khóa: {}, trang: {}, kích thước: {}", search, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Object[]> result;
        if (search == null || search.trim().isEmpty()) {
            result = sanPhamAdminRepository.findSanPhamWithTotalQuantityAndOrderByNgayTaoDesc(null, pageable);
        } else {
            result = sanPhamAdminRepository.findSanPhamWithTotalQuantityAndOrderByNgayTaoDesc(search.trim(), pageable);
        }

        List<SanPhamWithQuantity> sanPhamWithQuantities = result.getContent().stream()
                .map(obj -> {
                    SanPhamAdm sanPham = (SanPhamAdm) obj[0];
                    Long totalQuantity = (Long) obj[1];
                    return new SanPhamWithQuantity(sanPham, totalQuantity != null ? totalQuantity : 0);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(sanPhamWithQuantities, pageable, result.getTotalElements());
    }

    private void validate(SanPhamAdm object) {
        if (object.getTen() == null || object.getTen().trim().isEmpty()) {
            throw new SanPhamException("Tên sản phẩm không được để trống");
        }
        if (!object.getTen().trim().matches("^[\\p{L}\\s]+$")) {
            throw new SanPhamException("Tên sản phẩm chỉ được chứa chữ cái và khoảng trắng, không chứa số hoặc ký tự đặc biệt");
        }
        if (object.getTen().length() > 50) {
            throw new SanPhamException("Tên sản phẩm không được vượt quá 50 ký tự");
        }
        if (object.getTrangThai() != null && (object.getTrangThai() != 0 && object.getTrangThai() != 1 && object.getTrangThai() != 2)) {
            throw new SanPhamException("Trạng thái không hợp lệ, chỉ được phép là 0 (hết hàng), 1 (đang kinh doanh) hoặc 2 (tạm ngưng)");
        }
        Optional<SanPhamAdm> existing = sanPhamAdminRepository.findByTen(object.getTen().trim());
        if (existing.isPresent() && (object.getId() == null || !existing.get().getId().equals(object.getId()))) {
            throw new SanPhamException("Tên sản phẩm đã tồn tại");
        }
    }

    public static class SanPhamException extends RuntimeException {
        public SanPhamException(String message) {
            super(message);
        }
    }

    public static class SanPhamWithQuantity {
        private final SanPhamAdm sanPham;
        private final long totalQuantity;

        public SanPhamWithQuantity(SanPhamAdm sanPham, long totalQuantity) {
            this.sanPham = sanPham;
            this.totalQuantity = totalQuantity;
        }

        public SanPhamAdm getSanPham() {
            return sanPham;
        }

        public long getTotalQuantity() {
            return totalQuantity;
        }
    }
}
