package com.example.style_store_be.service.website;

import com.example.style_store_be.dto.request.DiaChiNhanRequest;
import com.example.style_store_be.dto.response.DiaChiNhanResponse;
import com.example.style_store_be.entity.DiaChiNhan;
import com.example.style_store_be.entity.User;
import com.example.style_store_be.mapper.DiaChiNhanMapper;
import com.example.style_store_be.repository.website.DiaChiNhanRepoSitory;
import com.example.style_store_be.repository.website.UserRepoSitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class DiaChiNhanService {
    DiaChiNhanRepoSitory diaChiNhanRepoSitory;
    DiaChiNhanMapper diaChiNhanMapper;
    UserRepoSitory userRepoSitory;

    public DiaChiNhan createDiaChiNhan(DiaChiNhanRequest diaChiNhanRequest) {
        DiaChiNhan diaChiNhan = diaChiNhanMapper.toDiaChiNhan(diaChiNhanRequest);
        diaChiNhan.setNgayTao(new Date());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepoSitory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        diaChiNhan.setUser(user);
        return diaChiNhanRepoSitory.save(diaChiNhan);
    }

    public List<DiaChiNhan> getAllDiaChiNhan() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepoSitory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        List<DiaChiNhan> diaChiNhanList = diaChiNhanRepoSitory.findAllByUser(user);
        return diaChiNhanList;
    }

    public DiaChiNhanResponse selectedDiaChiNhan(Long id) {
        return diaChiNhanMapper.toDiaChiNhanResponse(diaChiNhanRepoSitory.findById(id).orElseThrow(() -> new RuntimeException("Địa chỉ nhận không tồn tại")));
    }

    public void deleteDiaChiNhan(Long id) {
        DiaChiNhan diaChiNhan = diaChiNhanRepoSitory.findById(id)
                .orElseThrow(() -> new RuntimeException("Địa chỉ nhận không tồn tại"));
        diaChiNhanRepoSitory.deleteById(id);
    }
}
