package com.arkadiuszG.demo.service;

import com.arkadiuszG.demo.model.Product;
import com.arkadiuszG.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        Optional<Product> existing = productRepository.findByName(product.getName());
        if (existing.isPresent()) {
            throw new IllegalStateException("Product with this name already exists");
        }
        return productRepository.save(product);
    }
}
