package org.biukhanhhau.backend.repository;

import org.biukhanhhau.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsById(long id);
    boolean existsByName(String name);
}
