package com.example.style_store_be.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SercurityConfig {

    @Autowired
    private  CustomJwtDecoder customJwtDecoder;

    private final String [] PUBLIC_ENDPOINTS = {
            "/auth/dang-nhap",
            "/auth/refresh",
            "/auth/dang-xuat",
            "/auth/introspect",
            "/website/san-pham/**",
            "/api/upload",
            "/don-hang/**",
            "/api/admin-san-pham-chi-tiet/**",
            "/api/thuong-hieu/**",
            "/api/xuat-xu/**",
            "/api/admin-san-pham/**",
            "/api/mau-sac/**",
            "/api/kich-thuoc/**",
            "/api/hinh-anh-mau-sac/**",
            "/api/chat-lieu/**",
            "/api/admin/hoa-don-chi-tiet/**",
            "/api/hoa-don/**",
            "/api/admin/nguoi-dung/**",
            "/dia-chi-nhan/**",
            "/api/admin/thong-ke/**",
            "/api/thong-ke/**",
            "/api/admin/san-pham-ct/**",
            "/api/vnpay/**",
            "/api/admin/hoa-don/ma/**",
            "/api/admin/hoa-don/searchID/**"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.PUT, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.DELETE, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .decoder(customJwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );

        return http.build();
    }


    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("scope");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    //    @Bean
//    JwtDecoder jwtDecoder(){
//        SecretKeySpec  secretKeySpec = new SecretKeySpec(signerKey.getBytes(),"HS512");
//        return NimbusJwtDecoder
//                .withSecretKey(secretKeySpec)
//                .macAlgorithm(MacAlgorithm.HS512)
//                .build();
//    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}
