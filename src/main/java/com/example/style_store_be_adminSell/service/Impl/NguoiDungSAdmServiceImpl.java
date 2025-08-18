package com.example.style_store_be_adminSell.service.Impl;


import com.example.style_store_be.entity.User;
import com.example.style_store_be.exception.AppException;
import com.example.style_store_be.exception.Errorcode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.style_store_be_adminSell.dto.NguoiDungDto;
import com.example.style_store_be_adminSell.entity.DiaChiNhanSAdm;
import com.example.style_store_be_adminSell.entity.HoaDonSAdm;
import com.example.style_store_be_adminSell.entity.NguoiDungSAdm;
import com.example.style_store_be_adminSell.repository.DiaChiNhanSAdmRepo;
import com.example.style_store_be_adminSell.repository.NguoiDungSAdmRepo;
import com.example.style_store_be_adminSell.service.NguoiDungSAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class NguoiDungSAdmServiceImpl implements NguoiDungSAdmService {
    @Autowired
    private NguoiDungSAdmRepo nguoiDungSAdmRepo;

    @Autowired
    private DiaChiNhanSAdmRepo diaChiNhanSAdmRepo;

    JavaMailSender javaMailSender;
    PasswordEncoder passwordEncoder;

    private NguoiDungSAdm mapToNguoiDungEntity(NguoiDungDto dto) {
        NguoiDungSAdm entity = new NguoiDungSAdm();
        entity.setId(dto.getIdNguoiDung());
        entity.setMa(dto.getMaNguoiDung());
        entity.setHoTen(dto.getHoTen());
        entity.setSoDienThoai(dto.getSoDienThoai());
        entity.setEmail(dto.getEmail());
        entity.setCccd(dto.getCccd());
        entity.setDiaChi(dto.getDiaChiNguoiDung());
        entity.setGioiTinh(dto.getGioiTinh());
        entity.setNamSinh(dto.getNamSinh());
        entity.setTenDangNhap(dto.getTenDangNhap());
        entity.setMatKhau(dto.getMatKhau()); // → Nên mã hóa BCrypt trong Service
        entity.setTinh(dto.getTinhNguoiDung());
        entity.setHuyen(dto.getHuyenNguoiDung());
        entity.setXa(dto.getXaNguoiDung());
        entity.setIdChucVu(3L);
        entity.setTrangThai(1);
        entity.setNgayTao(new Date());
        return entity;
    }

    private DiaChiNhanSAdm mapToDiaChiEntity(NguoiDungDto dto, NguoiDungSAdm nguoiDung) {
        DiaChiNhanSAdm entity = new DiaChiNhanSAdm();
        entity.setId(dto.getIdDiaChi());
        entity.setMa(dto.getMaDiaChi());
        entity.setTenNguoiNhan(dto.getTenNguoiNhan());
        entity.setSoDienThoai(dto.getSoDienThoaiNhan());
        entity.setDiaChi(dto.getDiaChiNhan());
        entity.setTinh(dto.getTinhNhan());
        entity.setHuyen(dto.getHuyenNhan());
        entity.setXa(dto.getXaNhan());
        entity.setNguoiDungSAdm(nguoiDung);
        entity.setNgayTao(new Date());
        entity.setTrangThai(1);
        return entity;
    }

    private NguoiDungDto mapToDto(DiaChiNhanSAdm diaChi, NguoiDungSAdm nguoiDung) {
        NguoiDungDto dto = new NguoiDungDto();
        dto.setIdNguoiDung(nguoiDung.getId());
        dto.setMaNguoiDung(nguoiDung.getMa());
        dto.setHoTen(nguoiDung.getHoTen());
        dto.setSoDienThoai(nguoiDung.getSoDienThoai());
        dto.setEmail(nguoiDung.getEmail());
        dto.setCccd(nguoiDung.getCccd());
        dto.setDiaChiNguoiDung(nguoiDung.getDiaChi());
        dto.setGioiTinh(nguoiDung.getGioiTinh());
        dto.setNamSinh(nguoiDung.getNamSinh());
        dto.setTenDangNhap(nguoiDung.getTenDangNhap());
        dto.setMatKhau(nguoiDung.getMatKhau());
        dto.setTinhNguoiDung(nguoiDung.getTinh());
        dto.setHuyenNguoiDung(nguoiDung.getHuyen());
        dto.setXaNguoiDung(nguoiDung.getXa());
        dto.setIdChucVu(nguoiDung.getId());

        dto.setIdDiaChi(diaChi.getId());
        dto.setMaDiaChi(diaChi.getMa());
        dto.setTenNguoiNhan(diaChi.getTenNguoiNhan());
        dto.setSoDienThoai(diaChi.getSoDienThoai());
        dto.setDiaChiNhan(diaChi.getDiaChi());
        dto.setTinhNhan(diaChi.getTinh());
        dto.setHuyenNhan(diaChi.getHuyen());
        dto.setXaNhan(diaChi.getXa());

        return dto;
    }



    @Override
    public NguoiDungSAdm searchUserById(Long id) {
        return nguoiDungSAdmRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("Không tìm thấy người dùng"));
    }

    @Override
    public NguoiDungSAdm searchNguoiDungBySDT(String sdt,Long idChucVu) {
        return nguoiDungSAdmRepo.findBySoDienThoaiAndIdChucVu(sdt,idChucVu).orElseThrow(()-> new IllegalArgumentException("Không tìm thấy khách hàng"));
    }

    @Override
    public DiaChiNhanSAdm addNguoiDung(NguoiDungDto dto) {
        if (nguoiDungSAdmRepo.existsByEmail(dto.getEmail()))
            throw new AppException(Errorcode.EMAIL_EXISTED);

        NguoiDungSAdm nguoiDung = mapToNguoiDungEntity(dto);
        nguoiDung.setMa("nv" + UUID.randomUUID().toString().substring(0, 10));
        String rawPassword = "ph" + UUID.randomUUID().toString().substring(0, 10);
        nguoiDung.setMatKhau(passwordEncoder.encode(rawPassword));
        nguoiDung.setTenDangNhap(dto.getEmail());

        // Lưu người dùng trước
        nguoiDungSAdmRepo.save(nguoiDung);

        // Gửi email ngay sau khi lưu người dùng
        sendWelcomeEmail(nguoiDung, rawPassword);

        DiaChiNhanSAdm diaChi = mapToDiaChiEntity(dto, nguoiDung);

        // Lấy số nhà từ địa chỉ
        String fullAddress = diaChi.getDiaChi();
        if (fullAddress != null) {
            int count = 0;
            int pos = fullAddress.length();
            for (int i = fullAddress.length() - 1; i >= 0; i--) {
                if (fullAddress.charAt(i) == ',') {
                    count++;
                    if (count == 3) {
                        pos = i;
                        break;
                    }
                }
            }
            String soNha = fullAddress.substring(0, pos).trim();
            diaChi.setSoNha(soNha);
        } else {
            diaChi.setSoNha(null);
        }

        diaChi.setMa("DC" + UUID.randomUUID().toString().substring(0, 10));
        return diaChiNhanSAdmRepo.save(diaChi);
    }


    @Override
    public NguoiDungSAdm searchNguoiDungBySDT(String sdt) {
        return nguoiDungSAdmRepo.searchNguoiDungSAdmBySoDienThoai((sdt));
    }

    private void sendWelcomeEmail(NguoiDungSAdm user, String rawPassword) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(user.getEmail());
            helper.setSubject("Chào mừng bạn đến với hệ thống SD-02");

            String content = String.format(
                    "Xin chào %s,\n\n"
                            + "Tài khoản của bạn đã được tạo thành công trên hệ thống SD-02.\n"
                            + "Tên đăng nhập: %s\n"
                            + "Mật khẩu tạm thời: %s\n\n"
                            + "Vui lòng đăng nhập và đổi mật khẩu ngay sau lần đăng nhập đầu tiên.\n\n"
                            + "Trân trọng,\nĐội ngũ SD-02",
                    user.getHoTen(),
                    user.getTenDangNhap(),
                    rawPassword
            );

            helper.setText(content.replace("\n", "<br>"), true); // Gửi email dưới dạng HTML

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email tạo tài khoản: " + e.getMessage(), e);
        }
    }
}
