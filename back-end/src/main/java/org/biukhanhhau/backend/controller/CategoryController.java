package org.biukhanhhau.backend.controller;

import org.apache.coyote.Response;
import org.biukhanhhau.backend.model.Category;
import org.biukhanhhau.backend.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    CategoryService categoryService;
    CategoryController(CategoryService categoryService){
        this.categoryService =categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> findAllCategories(){
        List<Category> category = categoryService.findAllCategories();
        return ResponseEntity.ok(category);
    }

    @PostMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        Category category1 = categoryService.addCategory(category);
        return ResponseEntity.ok(category1);
    }

    @PutMapping("/categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable long id, @RequestBody Category category){
        Category category1 = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(category1);
    }

    @DeleteMapping("/categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
