package com.nimap.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.nimap.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	Page<Product> findAll(Pageable pageable);
}
