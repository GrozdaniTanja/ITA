package com.tanja.productservice.service;

import com.tanja.productservice.dto.ProductRequest;
import com.tanja.productservice.dto.ProductResponse;
import com.tanja.productservice.model.Product;
import com.tanja.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepositiry;


    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .productionCompany(productRequest.getProductionCompany())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepositiry.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public void editProduct(String id, ProductRequest productRequest) {
        Optional<Product> optionalProduct = productRepositiry.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setName(productRequest.getName());
            existingProduct.setProductionCompany(productRequest.getProductionCompany());
            existingProduct.setDescription(productRequest.getDescription());
            existingProduct.setPrice(productRequest.getPrice());
            productRepositiry.save(existingProduct);
            log.info("Product {} is updated", id);
        } else {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
    }

    public void deleteAllProducts() {
        productRepositiry.deleteAll();
        log.info("All products deleted");
    }

    public void deleteProductById(String id) {
        productRepositiry.deleteById(id);
        log.info("Product {} is deleted", id);
    }

    public ProductResponse getProductById(String id) {
        Optional<Product> optionalProduct = productRepositiry.findById(id);
        if (optionalProduct.isPresent()) {
            log.info("Product with ID {} found", id);
            return mapToProductResponse(optionalProduct.get());
        } else {
            log.warn("Product not found with ID {}", id);
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
    }


    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepositiry.findAll();
        if (!products.isEmpty()) {
            log.info("All products retrieved");
            return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
        } else {
            log.warn("No products found");
            return Collections.emptyList();
        }
    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .productionCompany(product.getProductionCompany())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }


}
