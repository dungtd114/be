package com.example.style_store_be.repository.website;

import com.example.style_store_be.dto.UserDto;
import com.example.style_store_be.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepoSitory extends JpaRepository<User,Long> {
    boolean existsByTenDangNhap(String tenDangNhap);

    Optional<User> findByEmail(String email);

    @Query("SELECT new com.example.style_store_be.dto.UserDto(" +
            "u.id, u.ma, u.hoTen, u.soDienThoai, u.email, u.cccd, u.diaChi, u.gioiTinh, " +
            "u.namSinh, u.tenDangNhap, u.matKhau, u.ngayTao, u.trangThai, r.ten) " +
            "FROM User u LEFT JOIN u.role r " +
            "WHERE r.id = 2 " +
            "AND (:hoTenOrSoDTOrEmail IS NULL OR " +
            "u.hoTen LIKE %:hoTenOrSoDTOrEmail% OR " +
            "u.soDienThoai LIKE %:hoTenOrSoDTOrEmail% OR " +
            "u.email LIKE %:hoTenOrSoDTOrEmail%) " +
            "AND (:gioiTinh IS NULL OR u.gioiTinh = :gioiTinh) " +
            "AND (:trangThai IS NULL OR u.trangThai = :trangThai) " +
            "ORDER BY u.ngayTao DESC")
    Page<UserDto> getPageStaff(@Param("hoTenOrSoDTOrEmail") String hoTenOrSoDTOrEmail,
                               @Param("gioiTinh") Integer gioiTinh,
                               @Param("trangThai") Integer trangThai,
                               Pageable pageable);


    @Query("SELECT new com.example.style_store_be.dto.UserDto(" +
            "u.id, u.ma, u.hoTen, u.soDienThoai, u.email, u.cccd, u.diaChi, u.gioiTinh, " +
            "u.namSinh, u.tenDangNhap, u.matKhau, u.ngayTao, u.trangThai, r.ten) " +
            "FROM User u LEFT JOIN u.role r " +
            "WHERE r.id = 3 " +
            "AND (:hoTenOrSoDTOrEmail IS NULL OR " +
            "u.hoTen LIKE %:hoTenOrSoDTOrEmail% OR " +
            "u.soDienThoai LIKE %:hoTenOrSoDTOrEmail% OR " +
            "u.email LIKE %:hoTenOrSoDTOrEmail%) " +
            "AND (:gioiTinh IS NULL OR u.gioiTinh = :gioiTinh) " +
            "AND (:trangThai IS NULL OR u.trangThai = :trangThai) " +
            "ORDER BY u.ngayTao DESC")
    Page<UserDto> getPageUser(@Param("hoTenOrSoDTOrEmail") String hoTenOrSoDTOrEmail,
                               @Param("gioiTinh") Integer gioiTinh,
                               @Param("trangThai") Integer trangThai,
                               Pageable pageable);

    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.trangThai = 0 WHERE u.id = :id")
    int deactivateUserById(@Param("id") Long id);

    boolean existsBySoDienThoai( String soDienThoai);
}
