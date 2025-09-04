package com.example.style_store_be.config;



import com.example.style_store_be.entity.Role;
import com.example.style_store_be.entity.User;
import com.example.style_store_be.repository.website.UserRepoSitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepoSitory userRepository) {
        return args -> {
            if (userRepository.findByEmail("hoa573898@gmail.com").isEmpty()) {
                Role adminRole = Role.builder()
                        .id(1L)
                        .build();

                User user = User.builder()
                        .ma("admin")
                        .hoTen("Trần Văn Hoà")
                        .gioiTinh(1)
                        .soDienThoai("0123456789")
                        .namSinh(new SimpleDateFormat("yyyy-MM-dd").parse("2005-01-01"))
                        .tenDangNhap("admin")
                        .email("hoa573898@gmail.com")
                        .matKhau(passwordEncoder.encode("admin"))
                        .role(adminRole)
                        .build();

                userRepository.save(user);
                log.warn("Default admin user created with username: admin and password: admin");
            }
        };
    }
}
