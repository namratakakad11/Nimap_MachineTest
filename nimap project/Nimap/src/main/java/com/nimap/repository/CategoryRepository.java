package com.nimap.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nimap.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	
	Page<Category> findAll(Pageable pageable);
	
	Optional<Category> findByName(String name);
}
