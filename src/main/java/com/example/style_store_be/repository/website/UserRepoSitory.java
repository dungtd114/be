package com.example.style_store_be.repository.website;

import com.example.style_store_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepoSitory extends JpaRepository<User,Long> {
    boolean existsByTenDangNhap(String tenDangNhap);

    Optional<User> findByEmail(String email);
}
