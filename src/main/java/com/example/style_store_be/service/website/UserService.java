package com.example.style_store_be.service.website;

import com.example.style_store_be.dto.UserDto;
import com.example.style_store_be.dto.request.UserCreationRequest;
import com.example.style_store_be.entity.Role;
import com.example.style_store_be.entity.User;
import com.example.style_store_be.exception.AppException;
import com.example.style_store_be.exception.Errorcode;
import com.example.style_store_be.mapper.UserMapper;
import com.example.style_store_be.repository.website.RoleRepoSitory;
import com.example.style_store_be.repository.website.UserRepoSitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class UserService {
    UserRepoSitory userRepoSitory;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    RoleRepoSitory roleRepoSitory;


    public User createUser(UserCreationRequest request) {
        if (userRepoSitory.existsByTenDangNhap(request.getTenDangNhap()))
            throw new AppException(Errorcode.USER_EXISTED);
        User user = userMapper.toUser(request);
        user.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
        Role role = roleRepoSitory.findById(3L).orElseThrow(()->new AppException(Errorcode.ROLE_NOT_FOUND));
        user.setRole(role);
        return userRepoSitory.save(user);
    }

    public Page<UserDto> getPageStaff(String hoTenOrSoDTOrEmail, Integer gioiTinh, Integer trangThai, Pageable pageable) {
        return userRepoSitory.getPageStaff(hoTenOrSoDTOrEmail, gioiTinh, trangThai, pageable);
    }

    public Page<UserDto> getPageUser(String hoTenOrSoDTOrEmail, Integer gioiTinh, Integer trangThai, Pageable pageable) {
        return userRepoSitory.getPageUser(hoTenOrSoDTOrEmail, gioiTinh, trangThai, pageable);
    }

    public User createStaff(UserCreationRequest request) {
        if (userRepoSitory.existsByTenDangNhap(request.getTenDangNhap()))
            throw new AppException(Errorcode.USER_EXISTED);
        User user = userMapper.toUser(request);
        user.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
        Role role = roleRepoSitory.findById(2L).orElseThrow(()->new AppException(Errorcode.ROLE_NOT_FOUND));
        user.setRole(role);
        return userRepoSitory.save(user);
    }
}
