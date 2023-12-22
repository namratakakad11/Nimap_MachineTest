package com.nimap.model;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {
	
	@Id   
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private int id;
	
	
	@Column(unique = true,nullable = false) 
	@Size(min = 5, message = " minimum 5 characters ")
	private String name;
	
	@CreatedDate
	private Instant createdAt;
	
	@LastModifiedDate
	private Instant updatedAt;

	@ManyToOne    
	@JoinColumn(name="category_id",referencedColumnName = "id")
	private Category category;

}
