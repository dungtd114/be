package com.example.style_store_be_adminSP.service.impl;

import com.example.style_store_be.entity.MauSacSp;
import com.example.style_store_be_adminSP.entity.HinhAnhMauSacAdm;
import com.example.style_store_be_adminSP.entity.MauSacSpAdm;
import com.example.style_store_be_adminSP.reposytory.HinhAnhRepoAdm;
import com.example.style_store_be_adminSP.reposytory.MauSacSPRepoAdm;
import com.example.style_store_be_adminSP.service.HinhAnhMauSacServiceAdm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class HinhAnhMauSacServiceImplAdm implements HinhAnhMauSacServiceAdm {

    private static final Logger logger = LoggerFactory.getLogger(HinhAnhMauSacServiceImplAdm.class);

    @Autowired
    private HinhAnhRepoAdm repository;
    @Autowired
    private MauSacSPRepoAdm mauSacRepo;


    @Value("${upload.dir:D:/DATN/style-store-be/src/uploads}")
    private String uploadDir;

    // Phương thức tiện ích để định dạng đường dẫn hình ảnh
    private String formatHinhAnh(String hinhAnh, LocalDateTime ngayXoa) {
        if (hinhAnh != null && !hinhAnh.isEmpty()) {
            if (ngayXoa == null && !hinhAnh.startsWith("/uploads/")) {
                return "/uploads/" + hinhAnh;
            } else if (ngayXoa != null) {
                // Sau khi xóa, loại bỏ tiền tố /uploads/ để tránh nhầm lẫn
                return hinhAnh.replace("/uploads/", "");
            }
        }
        return hinhAnh;
    }

    // Phương thức tiện ích để xóa file
    private void deleteFile(String fileName) {
        File file = new File(uploadDir + File.separator + fileName);
        if (file.exists()) {
            try {
                if (file.delete()) {
                    logger.info("Đã xóa file vật lý: {}", fileName);
                } else {
                    logger.warn("Không thể xóa file vật lý: {}", fileName);
                }
            } catch (SecurityException e) {
                logger.error("Lỗi quyền truy cập khi xóa file {}: {}", fileName, e.getMessage());
            }
        }
    }

    @Override
    public Page<HinhAnhMauSacAdm> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HinhAnhMauSacAdm> pageResult = repository.findAll(pageable);
        pageResult.getContent().forEach(h -> h.setHinhAnh(formatHinhAnh(h.getHinhAnh(), h.getNgayXoa())));
        return pageResult;
    }

    @Override
    public Page<HinhAnhMauSacAdm> searchByHinhAnh(String hinhAnh, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HinhAnhMauSacAdm> pageResult = repository.findByHinhAnhContainingIgnoreCase(hinhAnh, pageable);
        pageResult.getContent().forEach(h -> h.setHinhAnh(formatHinhAnh(h.getHinhAnh(), h.getNgayXoa())));
        return pageResult;
    }

    @Override
    public Page<HinhAnhMauSacAdm> getActive(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HinhAnhMauSacAdm> pageResult = repository.findByNgayXoaIsNull(pageable);
        pageResult.getContent().forEach(h -> h.setHinhAnh(formatHinhAnh(h.getHinhAnh(), h.getNgayXoa())));
        return pageResult;
    }

    @Override
    public Page<HinhAnhMauSacAdm> getDeleted(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HinhAnhMauSacAdm> pageResult = repository.findByNgayXoaIsNotNull(pageable);
        pageResult.getContent().forEach(h -> h.setHinhAnh(formatHinhAnh(h.getHinhAnh(), h.getNgayXoa())));
        return pageResult;
    }

    @Override
    public HinhAnhMauSacAdm getOne(Long id) {
        HinhAnhMauSacAdm hinhAnh = repository.findById(id)
                .orElseThrow(() -> new HinhAnhMauSacNotFoundException("Hình ảnh màu sắc không tồn tại với id: " + id));
        hinhAnh.setHinhAnh(formatHinhAnh(hinhAnh.getHinhAnh(), hinhAnh.getNgayXoa()));
        return hinhAnh;
    }

    @Override
    public void add(HinhAnhMauSacAdm hinhAnhMauSac) {
        if (hinhAnhMauSac.getHinhAnh() == null || hinhAnhMauSac.getHinhAnh().isEmpty()) {
            throw new IllegalArgumentException("Đường dẫn hình ảnh không được rỗng");
        }
        hinhAnhMauSac.setNgayTao(LocalDateTime.now());
        hinhAnhMauSac.setTrangThai(1);
        repository.save(hinhAnhMauSac);
        logger.info("Thêm mới hình ảnh thành công: {}", hinhAnhMauSac.getHinhAnh());
    }

    @Override
    public void update(HinhAnhMauSacAdm hinhAnhMauSac) {
        HinhAnhMauSacAdm existing = getOne(hinhAnhMauSac.getId());
        if (hinhAnhMauSac.getHinhAnh() != null && !hinhAnhMauSac.getHinhAnh().isEmpty()) {
            existing.setHinhAnh(hinhAnhMauSac.getHinhAnh());
        }
        existing.setMoTa(hinhAnhMauSac.getMoTa());
        existing.setMauSac(hinhAnhMauSac.getMauSac());
        existing.setTrangThai(hinhAnhMauSac.getTrangThai());
        existing.setNgaySua(LocalDateTime.now());
        repository.save(existing);
        logger.info("Cập nhật hình ảnh thành công với id: {}", existing.getId());
    }

    @Override
    public void delete(Long id) {
        HinhAnhMauSacAdm existing = getOne(id);
        String fileName = existing.getHinhAnh();
        existing.setNgayXoa(LocalDateTime.now());
        existing.setTrangThai(0);
        repository.save(existing);
        if (fileName != null && !fileName.isEmpty()) {
            deleteFile(fileName);
        }
        logger.info("Xóa mềm hình ảnh thành công với id: {}", id);
    }

    @Override
    public List<HinhAnhMauSacAdm> getByMauSacId(Long mauSacId) {
        List<HinhAnhMauSacAdm> hinhAnhList = repository.findByMauSacIdAndActive(mauSacId);
        hinhAnhList.forEach(h -> h.setHinhAnh(formatHinhAnh(h.getHinhAnh(), h.getNgayXoa())));
        return hinhAnhList;
    }

    @Override
    public String uploadImage(MultipartFile file, Long mauSacId) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File không được rỗng");
        }

        if (file.getSize() > 5 * 1024 * 1024) { // Giới hạn 5MB
            throw new IllegalArgumentException("File vượt quá kích thước cho phép");
        }

        String extension = file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf("."))
                .toLowerCase();

        if (!Arrays.asList(".jpg", ".jpeg", ".png").contains(extension)) {
            throw new IllegalArgumentException("Định dạng file không được hỗ trợ");
        }

        try {
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                throw new IllegalArgumentException("Tên file không hợp lệ");
            }

            String baseName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = originalFilename;
            File dest = new File(uploadDir + File.separator + uniqueFileName);

            if (!dest.exists()) {
                file.transferTo(dest); // chỉ lưu nếu chưa tồn tại
            } else {
                logger.info("File đã tồn tại, dùng lại: {}", uniqueFileName);
            }


            // ✅ Lưu vào bảng hình ảnh màu sắc
            HinhAnhMauSacAdm entity = new HinhAnhMauSacAdm();
            entity.setHinhAnh(uniqueFileName);
            entity.setNgayTao(LocalDateTime.now());
            entity.setTrangThai(1);

            MauSacSp mauSacRef = new MauSacSp(); // chỉ cần set id
            mauSacRef.setId(mauSacId);
            entity.setMauSac(mauSacRef);

            repository.save(entity);
            logger.info("Tải lên thành công file: {}", uniqueFileName);
            // ✅ GÁN ảnh này vào bảng màu sắc làm ảnh chính
            MauSacSpAdm mauSac = mauSacRepo.findById(mauSacId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc với ID: " + mauSacId));
            mauSac.setHinhAnhMauSac(entity); // Gán ảnh đại diện
            mauSacRepo.save(mauSac);

            return uniqueFileName;

        } catch (IOException e) {
            logger.error("Lỗi khi lưu file: {}", e.getMessage());
            throw new RuntimeException("Lỗi khi lưu file: " + e.getMessage());
        }
    }

}

// Ngoại lệ tùy chỉnh
class HinhAnhMauSacNotFoundException extends RuntimeException {
    public HinhAnhMauSacNotFoundException(String message) {
        super(message);
    }
}