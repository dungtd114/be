package com.example.style_store_be.service.website;

import com.example.style_store_be.dto.UserDto;
import com.example.style_store_be.dto.request.UserCreationRequest;
import com.example.style_store_be.dto.request.UserUpdateRequest;
import com.example.style_store_be.dto.response.UserResponse;
import com.example.style_store_be.entity.Role;
import com.example.style_store_be.entity.User;
import com.example.style_store_be.exception.AppException;
import com.example.style_store_be.exception.Errorcode;
import com.example.style_store_be.mapper.UserMapper;
import com.example.style_store_be.repository.website.RoleRepoSitory;
import com.example.style_store_be.repository.website.UserRepoSitory;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class UserService {
    UserRepoSitory userRepoSitory;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    RoleRepoSitory roleRepoSitory;
    JavaMailSender javaMailSender;

    public User createUser(UserCreationRequest request) {

        if (userRepoSitory.existsByEmail(request.getEmail()))
            throw new AppException(Errorcode.EMAIL_EXISTED);
        if (userRepoSitory.existsBySoDienThoai(request.getSoDienThoai()))
            throw new AppException(Errorcode.PHONE_EXISTED);
        User user = userMapper.toUser(request);
        user.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
        Role role = roleRepoSitory.findById(3L).orElseThrow(()->new AppException(Errorcode.ROLE_NOT_FOUND));
        user.setRole(role);
        user.setNgayTao(new Date());
        user.setMa("us-"+ UUID.randomUUID().toString().substring(0, 10));
        user.setTrangThai(1);
        return userRepoSitory.save(user);
    }

    public Page<UserDto> getPageStaff(String hoTenOrSoDTOrEmail, Integer gioiTinh, Integer trangThai, Pageable pageable) {
        return userRepoSitory.getPageStaff(hoTenOrSoDTOrEmail, gioiTinh, trangThai, pageable);
    }

    public Page<UserDto> getPageUser(String hoTenOrSoDTOrEmail, Integer gioiTinh, Integer trangThai, Pageable pageable) {
        return userRepoSitory.getPageUser(hoTenOrSoDTOrEmail, gioiTinh, trangThai, pageable);
    }

    public User createStaff(UserCreationRequest request) {
        if (userRepoSitory.existsByEmail(request.getEmail()))
            throw new AppException(Errorcode.EMAIL_EXISTED);
        if (userRepoSitory.existsBySoDienThoai(request.getSoDienThoai()))
            throw new AppException(Errorcode.PHONE_EXISTED);

        User user = userMapper.toUser(request);

        String rawPassword = "ph" + UUID.randomUUID().toString().substring(0, 10);
        user.setMatKhau(passwordEncoder.encode(rawPassword));

        Role role = roleRepoSitory.findById(2L)
                .orElseThrow(() -> new AppException(Errorcode.ROLE_NOT_FOUND));
        user.setRole(role);

        user.setMa("nv" + UUID.randomUUID().toString().substring(0, 10));
        user.setTenDangNhap(request.getTenDangNhap());
        user.setNgayTao(new Date());

        User savedUser = userRepoSitory.save(user);

        sendWelcomeEmail(savedUser, rawPassword); // ✅ Gửi email

        return savedUser;
    }



    public UserResponse getUserDetail(Long id) {
        User user = userRepoSitory.findById(id)
                .orElseThrow(() -> new AppException(Errorcode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
    public UserResponse getMyInfo() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepoSitory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        Long id= user.getId();
        User userDetail = userRepoSitory.findById(id)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        return userMapper.toUserResponse(userDetail);


    }

    public UserResponse updateUser(Long id, UserUpdateRequest updaterequest) {

        User user = userRepoSitory.findById(id).orElseThrow(()-> new RuntimeException("User không tồn tại"));
        userMapper.userUpdateRequest(user,updaterequest);
        user.setNgaySua(new Date());
        return userMapper.toUserResponse(userRepoSitory.save(user));
    }
    public UserResponse updateMyInfo(String email,  UserUpdateRequest request) {
        User user = userRepoSitory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        userMapper.userUpdateRequest(user, request);
        user.setNgaySua(new Date());
        return userMapper.toUserResponse(userRepoSitory.save(user));
    }


    public void removeUser(Long id) {
        int updated = userRepoSitory.deactivateUserById(id);
        if (updated == 0) {
            throw new AppException(Errorcode.USER_NOT_EXISTED);
        }
    }

    public User createrCustomer(UserCreationRequest request) {
        if (userRepoSitory.existsByEmail(request.getEmail()))
            throw new AppException(Errorcode.EMAIL_EXISTED);
        if (userRepoSitory.existsBySoDienThoai(request.getSoDienThoai()))
            throw new AppException(Errorcode.PHONE_EXISTED);

        User user = userMapper.toUser(request);

        String rawPassword = "ph" + UUID.randomUUID().toString().substring(0, 10);
        user.setMatKhau(passwordEncoder.encode(rawPassword));

        Role role = roleRepoSitory.findById(3L)
                .orElseThrow(() -> new AppException(Errorcode.ROLE_NOT_FOUND));
        user.setRole(role);

        user.setMa("nv" + UUID.randomUUID().toString().substring(0, 10));
        user.setTenDangNhap(request.getTenDangNhap());
        user.setNgayTao(new Date());

        User savedUser = userRepoSitory.save(user);

        sendWelcomeEmail(savedUser, rawPassword); // ✅ Gửi email

        return savedUser;
    }



    private void sendWelcomeEmail(User user, String rawPassword) {
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
