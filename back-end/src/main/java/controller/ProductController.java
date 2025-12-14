package controller;

import service.ProductService;
import model.Product;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Product> addProduct(@RequestBody Product Product){
        Product pro = ProductService.addProduct(Product);
        return ResponseEntity.ok(pro);
    }

    @PutMapping("products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product){
        Product pro = ProductService.updateProduct(id, product);
        return ResponseEntity.ok(pro);
    }
}
