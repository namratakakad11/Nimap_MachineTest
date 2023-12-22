package com.nimap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nimap.model.Category;
import com.nimap.model.Product;
import com.nimap.repository.CategoryRepository;
import com.nimap.repository.ProductRepository;
import com.nimap.responsewrapper.ResponseWrapper;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public ResponseEntity<?> getAllProducts(int page,int pagesize)
	{
		Pageable pageData=PageRequest.of(page -1, pagesize);
		Page<Product> data=productRepository.findAll(pageData);
		System.out.println(data);
		if(data.hasNext())
		{
		    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There is no Data in database please add some");	
		}
		else
		{
			ResponseWrapper rw =new ResponseWrapper();
			rw.setData(data);
			rw.setMessage("Product Founded");
			return new ResponseEntity<>(rw,HttpStatus.FOUND);
		}
		
	}
	
	
	
	public ResponseEntity<?> addProduct(Product product)
	{
		String category_found = product.getCategory().getName();
//		System.out.println(category_found);
		Category category=categoryRepository.findByName(category_found).
				          orElseThrow(()->
				          {
                     		throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There is no Category with name "+category_found);
		                  }
				          );
		
		product.setCategory(category);
	    Product newprod = productRepository.save(product);
		if(newprod==null)
		{
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Product value is null.");
		}
		else
		{
			ResponseWrapper rw =new ResponseWrapper();
			rw.setData(newprod);
			rw.setMessage("Product Added Sucessfully.");
			return new ResponseEntity<>(rw,HttpStatus.CREATED);
		}
		
	}
	
	public ResponseEntity<?> getProductById(int id)
	{
		Product productFound=productRepository.findById(id).
				             orElseThrow(()->
				             {
			                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product With Id "+id+" not Exist.");
		                     }
				             );
		ResponseWrapper rw =new ResponseWrapper();
		rw.setData(productFound);
		rw.setMessage("Following Data Found");
		return new ResponseEntity<>(rw,HttpStatus.FOUND);
	}
	
	public ResponseEntity<?> updateById(Product product,int id)
	{
		Product productFound=productRepository.findById(id).
				             orElseThrow(()->{
			                                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product Id does not Exist.");
		                                     });
		product.setId(productFound.getId());	
		String category_found=product.getCategory().getName();
		Category category=categoryRepository.findByName(category_found).
				          orElseThrow(()->{
			                                 throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not category with name "+category_found);
		                                   });
		product.setCategory(category);
		product.setCreatedAt(productFound.getCreatedAt());
		category.setCreatedAt(category.getCreatedAt());
		Product updatedProduct=productRepository.save(product);
		ResponseWrapper rw =new ResponseWrapper();
		rw.setData(updatedProduct);
		rw.setMessage("Product With Id "+id+" updated sucessfully");
		return new ResponseEntity<>(rw,HttpStatus.CREATED);
		
	}
	
	public ResponseEntity<?> deleteById(int id)
	{
		getProductById(id);
		productRepository.deleteById(id);
		ResponseWrapper rw =new ResponseWrapper();
		rw.setData(" ");
		rw.setMessage("Product With Id "+id+" Deleted");
		return new ResponseEntity<>(rw,HttpStatus.OK);
	}
	
	
	
	

}
