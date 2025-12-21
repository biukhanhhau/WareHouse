package org.biukhanhhau.backend.controller;

import org.biukhanhhau.backend.service.ProductService;
import org.biukhanhhau.backend.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    ProductService ProductService;
    ProductController(ProductService ProductService){
        this.ProductService = ProductService;
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
    public ResponseEntity<Product> addProduct(@RequestBody Product Product){
        Product pro = ProductService.addProduct(Product);
        return ResponseEntity.ok(pro);
    }

    @PutMapping("products/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product){
        Product pro = ProductService.updateProduct(id, product);
        return ResponseEntity.ok(pro);
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id){
        ProductService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
