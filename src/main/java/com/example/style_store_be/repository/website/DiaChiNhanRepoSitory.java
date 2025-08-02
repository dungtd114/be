package com.example.style_store_be.repository.website;

import com.example.style_store_be.entity.DiaChiNhan;
import com.example.style_store_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaChiNhanRepoSitory extends JpaRepository<DiaChiNhan,Long> {

    List<DiaChiNhan> findAllByUser(User user);
}
