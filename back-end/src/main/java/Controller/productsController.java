package Controller;

import Service.productsService;
import model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class productsController {

    productsService productsService;
    productsController(productsService productsService){
        this.productsService = productsService;
    }

    @GetMapping("products")
    public ResponseEntity<List<Product>> findAllProducts(){
        List<Product> Product = productsService.getProducts();
        return ResponseEntity.status(200).body(Product);
    }

    @GetMapping("products/{id}")
    public ResponseEntity<Optional<Product>> findById(@RequestParam int id){
        Optional<Product> product = productsService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("products")
    public ResponseEntity<Product> addProduct(@RequestBody Product Product){
        Product pro = productsService.addProduct(Product);
        return ResponseEntity.ok(pro);
    }

    @PutMapping("products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product){
        Product pro = productsService.updateProduct(id, product);
        return ResponseEntity.ok(pro);
    }
}
