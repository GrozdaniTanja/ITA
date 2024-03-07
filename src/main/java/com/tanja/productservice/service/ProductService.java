package com.tanja.productservice.service;

import com.tanja.productservice.dto.ProductRequest;
import com.tanja.productservice.dto.ProductResponse;
import com.tanja.productservice.model.Product;
import com.tanja.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<ProductResponse> getAllProducts() {
        List<Product> products= productRepositiry.findAll();

        return  products.stream().map(this::mapToProductResponse).toList();
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
