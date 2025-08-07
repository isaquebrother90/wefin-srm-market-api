package com.wefin.srm.domain.repository;

import com.wefin.srm.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}