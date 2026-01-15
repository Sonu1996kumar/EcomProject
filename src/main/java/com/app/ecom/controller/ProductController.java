package com.app.ecom.controller;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProduct(){
        return  ResponseEntity.ok(productService.getAllProduct());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest){
        //return new ResponseEntity<ProductResponse>(productService.updateProduct(id, productRequest), HttpStatus.OK);
        return productService.updateProduct(id, productRequest)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        //return new ResponseEntity<ProductResponse>(productService.updateProduct(id, productRequest), HttpStatus.OK);
        boolean deletedProduct = productService.deleteProduct(id);
        return deletedProduct ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyWord){
        return  ResponseEntity.ok(productService.searchProducts(keyWord));
    }
}
