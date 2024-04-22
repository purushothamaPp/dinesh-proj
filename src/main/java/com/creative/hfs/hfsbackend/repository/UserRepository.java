package com.creative.hfs.hfsbackend.repository;

import com.creative.hfs.hfsbackend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUserId(Integer userId);
}
