package org.biukhanhhau.backend.service;

import org.biukhanhhau.backend.repository.ProductRepository;
import org.biukhanhhau.backend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository repo;

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

        proTemp.setName(product.getName());
        proTemp.setPrice(product.getPrice());
        proTemp.setQuantity(product.getQuantity());
        proTemp.setSku(product.getSku());
        proTemp.setDescription(product.getDescription());

        return repo.save(proTemp);
    }
}
