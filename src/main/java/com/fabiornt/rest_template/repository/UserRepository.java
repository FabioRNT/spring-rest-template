package com.fabiornt.rest_template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabiornt.rest_template.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
