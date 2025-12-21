package org.biukhanhhau.backend.service;

import org.biukhanhhau.backend.repository.ProductRepository;
import org.biukhanhhau.backend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    ProductRepository repo;
    ProductService(ProductRepository repo){
        this.repo = repo;
    }

    public List<Product> getProducts() {
        return repo.findAll();
    }

    public Optional<Product> findById(int id) {
        return repo.findById(Long.valueOf(id));
    }

    public Product addProduct(Product Product) {
        return repo.save(Product);
    }

    public Product updateProduct(int id, Product product) {
        Product proTemp = repo.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if(product.getName() != null){
            proTemp.setName(product.getName());
        }
        if(product.getPrice() != null){
            proTemp.setPrice(product.getPrice());
        }
        if(product.getQuantity() != null){
            proTemp.setQuantity(product.getQuantity());
        }
        if(product.getSku() != null){
            proTemp.setSku(product.getSku());
        }
        if(product.getDescription() != null){
            proTemp.setDescription(product.getDescription());
        }
        if(product.getCategory() != null){
            proTemp.setCategory(product.getCategory());
        }

        return repo.save(proTemp);
    }

    public void deleteProduct(int id) {
        if(!repo.existsById(Long.valueOf(id))){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist");
        }
        try{
            repo.deleteById(Long.valueOf(id));
        }
        catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete because this product is in use");
        }
    }
}
