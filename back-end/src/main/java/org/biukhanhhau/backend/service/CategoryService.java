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

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findById(long id){
        return categoryRepository.findById(id)
                .orElse(null);
    }

    public Category addCategory(Category category){
        if(categoryRepository.existsByName(category.getName())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This category name is existed");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(long id, Category category){
        if (categoryRepository.existsByName(findById(id).getName())){
            categoryRepository.save(category);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This category is not existed");
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
