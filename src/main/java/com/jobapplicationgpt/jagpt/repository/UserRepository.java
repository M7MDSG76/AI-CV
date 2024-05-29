package com.jobapplicationgpt.jagpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jobapplicationgpt.jagpt.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    
}
