package org.biukhanhhau.backend.controller;

import jakarta.validation.Valid;
import org.biukhanhhau.backend.dto.ProductDTO;
import org.biukhanhhau.backend.model.Category;
import org.biukhanhhau.backend.repository.CategoryRepository;
import org.biukhanhhau.backend.service.ProductService;
import org.biukhanhhau.backend.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    ProductService ProductService;
    CategoryRepository categoryRepository;
    ProductController(ProductService ProductService, CategoryRepository categoryRepository){
        this.ProductService = ProductService;
        this.categoryRepository =categoryRepository;
    }

    @GetMapping("products")
    public ResponseEntity<List<Product>> findAllProducts(){
        List<Product> Product = ProductService.getProducts();
        return ResponseEntity.status(200).body(Product);
    }

    @GetMapping("products/{id}")
    public ResponseEntity<Optional<Product>> findById(@PathVariable int id){
        Optional<Product> product = ProductService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDTO productDTO){
        if(ProductService.existByName(productDTO.getName())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "this name already existed");
        }
        Category category = categoryRepository.findById(productDTO.getCategoryId().longValue())
                .orElseThrow(() -> new RuntimeException("Cannot find the category!"));
        Product newProduct = new Product();
        newProduct.setName(productDTO.getName());
        newProduct.setDescription(productDTO.getDescription());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setQuantity(productDTO.getQuantity());
        newProduct.setSku(productDTO.getSku());
        newProduct.setCategory(category);

        Product pro = ProductService.addProduct(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(pro);
    }

    @PutMapping("products/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Valid
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody ProductDTO productDTO){
        Product newProduct = new Product();
        newProduct.setName(productDTO.getName());
        newProduct.setDescription(productDTO.getDescription());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setQuantity(productDTO.getQuantity());
        newProduct.setSku(productDTO.getSku());

        if (productDTO.getCategoryId() != null){
            Category category = categoryRepository.findById(Long.valueOf(productDTO.getCategoryId()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found this category"));
            newProduct.setCategory(category);
        }

        Product pro = ProductService.updateProduct(id, newProduct);
        return ResponseEntity.ok(pro);
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id){
        ProductService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
