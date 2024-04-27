package com.tanja.productservice.controller;

import com.tanja.productservice.dto.ProductRequest;
import com.tanja.productservice.dto.ProductResponse;
import com.tanja.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        LOGGER.info("Creating product: {}", productRequest);
        productService.createProduct(productRequest);
        LOGGER.info("Product created successfully");

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        LOGGER.info("Retrieving all products");
        List<ProductResponse> products =  productService.getAllProducts();
        LOGGER.info("Retrieved {} products", products.size());
        return products;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editProduct(@PathVariable String id, @RequestBody ProductRequest productRequest) {
        LOGGER.info("Editing product with ID {}: {}", id, productRequest);
        productService.editProduct(id, productRequest);
        LOGGER.info("Product with ID {} edited successfully", id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable String id) {
        LOGGER.info("Deleting product with ID {}", id);
        productService.deleteProductById(id);
        LOGGER.info("Product with ID {} deleted successfully", id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductById(@PathVariable String id) {

        LOGGER.info("Retrieving product with ID {}", id);
        ProductResponse product = productService.getProductById(id);
        LOGGER.info("Retrieved product: {}", product);
        return product;
    }


}
