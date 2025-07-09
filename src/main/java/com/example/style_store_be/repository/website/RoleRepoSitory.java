package com.example.style_store_be.repository.website;

import com.example.style_store_be.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepoSitory extends JpaRepository<Role,Long> {
}
