package com.tanja.productservice.service;

import com.tanja.productservice.dto.ProductRequest;
import com.tanja.productservice.dto.ProductResponse;
import com.tanja.productservice.model.Product;
import com.tanja.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepositiry;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);



    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .productionCompany(productRequest.getProductionCompany())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepositiry.save(product);
        LOGGER.debug("Product {} is saved", product.getId());
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
            LOGGER.debug("Product {} is updated", id);
        } else {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
    }

    public void deleteProductById(String id) {
        productRepositiry.deleteById(id);
        LOGGER.debug("Product {} is deleted", id);
    }

    public ProductResponse getProductById(String id) {
        Optional<Product> optionalProduct = productRepositiry.findById(id);
        if (optionalProduct.isPresent()) {
            LOGGER.debug("Product with ID {} found", id);
            return mapToProductResponse(optionalProduct.get());
        } else {
            LOGGER.debug("Product not found with ID {}", id);
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
    }


    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepositiry.findAll();
        if (!products.isEmpty()) {
            LOGGER.debug("All products retrieved");
            return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
        } else {
            LOGGER.debug("No products found");
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
