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
import com.nimap.repository.CategoryRepository;
import com.nimap.responsewrapper.ResponseWrapper;


@Service
public class CategoryService {
	
	@Autowired        
	CategoryRepository categoryRepository;
	
	public ResponseEntity<?> getAllCategory(int page ,int pagesize) 
	{
	  Pageable pagedata = PageRequest.of(page -1, pagesize);
	  
	  Page<Category> data = categoryRepository.findAll(pagedata);
	  
	  if(data.hasNext())
	  {
		  throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There is no data in database please add some.");
	  }
	  else
	  {
		  ResponseWrapper rw = new ResponseWrapper();
		  rw.setData(data);
		  rw.setMessage("Category Founded");
		  return new ResponseEntity<>(rw,HttpStatus.FOUND);
	  }
	}
	
	public ResponseEntity<?> addNewCategory(Category category)  
	{
		Category data=categoryRepository.save(category);
		if(data==null)
		{
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"No data Found");
		}
		else
		{
			ResponseWrapper rw = new ResponseWrapper();
			rw.setData(data);
			rw.setMessage("Following Category Is Added.");
			return new ResponseEntity<>(rw,HttpStatus.ACCEPTED);
		}
	}
	
	public ResponseEntity<?> getCategoryById(int id) 
	{
         Category data=categoryRepository.findById(id).
        		       orElseThrow(()->{
        		    	             	throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category with "+id+" Not Exist.");
				                       }
			                      );
         ResponseWrapper rw = new ResponseWrapper();
         rw.setData(data);
         rw.setMessage("Category with "+id+" Founded.");
         return new ResponseEntity<>(rw,HttpStatus.FOUND);
	}

	
	public ResponseEntity<?> updateCategory(Category category,int id) 
	{
		Category foundedCategory=categoryRepository.findById(id).
				                 orElseThrow(()->
				                 {
					              	throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category with Id "+id+" Not Exist.");
					             });
		ResponseWrapper rw = new ResponseWrapper();
		category.setId(foundedCategory.getId());
		Category updated_category=categoryRepository.save(category);
		rw.setData(updated_category);
		rw.setMessage("Category with Id "+id+" Updated Sucessfully.");
		return new ResponseEntity<>(rw,HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> deleteById(int id) 
	{
		getCategoryById(id);
		categoryRepository.deleteById(id);
		ResponseWrapper rw = new ResponseWrapper();
		rw.setData(" ");
		rw.setMessage("Category Deleted Sucessfully");
		return new ResponseEntity<>(rw,HttpStatus.ACCEPTED);
	}
	
}
