package com.wefin.srm.domain.repository;

import com.wefin.srm.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
