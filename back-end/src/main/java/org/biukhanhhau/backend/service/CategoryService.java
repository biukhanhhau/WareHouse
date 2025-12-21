package org.biukhanhhau.backend.service;

import org.apache.coyote.Response;
import org.biukhanhhau.backend.model.Category;
import org.biukhanhhau.backend.repository.CategoryRepository;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;
    CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository =categoryRepository;
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findById(long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

    public Category addCategory(Category category){
        if(categoryRepository.existsByName(category.getName())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This category name is existed");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(long id, Category category){
        Category existingCate = findById(id);
        if (!existingCate.getName().equals(category.getName())
             && categoryRepository.existsByName(category.getName())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists");
        }
        if(category.getName() != null){
            existingCate.setName(category.getName());
        }
        return categoryRepository.save(existingCate);
    }

    public void deleteCategory(long id) {
        if(!categoryRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find the category");
        }
        try{
            categoryRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){// if it was in a foreign relationship
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete the category because of having product(s) inside");
        }
    }
}
