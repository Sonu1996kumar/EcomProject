package com.app.ecom.service;

import com.app.ecom.bo.Product;
import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setActive(savedProduct.getActive());
        response.setDescription(savedProduct.getDescription());
        response.setCategory(savedProduct.getCategory());
        response.setPrice(savedProduct.getPrice());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setStockQuantity(savedProduct.getStockQuantity());

        return response;
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
    }

    /*public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id).map(existingProduct->{
            updateProductFromRequest(existingProduct, productRequest);
            Product savedProduct = productRepository.save(existingProduct);
            return mapToProductResponse(savedProduct);
        }).orElseThrow(()->new RuntimeException("Product Not Found "+ id));
    }*/
    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id).map(existingProduct -> {
            updateProductFromRequest(existingProduct, productRequest);
            Product savedProduct = productRepository.save(existingProduct);
            return mapToProductResponse(savedProduct);
        });
    }

    public List<ProductResponse> getAllProduct() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);   // soft delete
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }


    public List<ProductResponse> searchProducts(String keyWord) {
        return productRepository.searchProducts(keyWord).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
}
