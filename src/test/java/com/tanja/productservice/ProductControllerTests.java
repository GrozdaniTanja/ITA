package com.tanja.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanja.productservice.dto.ProductRequest;
import com.tanja.productservice.model.Product;
import com.tanja.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    private static final Logger logger = LoggerFactory.getLogger(ProductControllerTests.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void testGetProductById() throws Exception {
        logger.debug("Starting testGetProductById...");

        ProductRequest productRequest = getProductRequest();
        String productRequestString = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestString))
                .andExpect(status().isCreated());

        Product createdProduct = productRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/" + createdProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(createdProduct.getId()));
        logger.debug("Finished testGetProductById.");
    }

    @Test
    void testGetAllProducts() throws Exception {
        logger.debug("Starting testGetAllProducts...");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
        logger.debug("Finished testGetAllProducts.");
    }


    @Test
    public void testCreateProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        String productRequestString = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestString))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, productRepository.findAll().size());
    }

    @Test
    void testEditProduct() throws Exception {
        logger.debug("Starting testEditProduct...");
        ProductRequest productRequest = getProductRequest();
        String productRequestString = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestString))
                .andExpect(status().isCreated());

        Product createdProduct = productRepository.findAll().get(0);

        productRequest.setName("UpdatedName");

        String updateProductRequestString = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/product/" + createdProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateProductRequestString))
                .andExpect(status().isOk());

        Product updatedProduct = productRepository.findById(createdProduct.getId()).orElse(null);
        Assertions.assertNotNull(updatedProduct);
        Assertions.assertEquals("UpdatedName", updatedProduct.getName());
        logger.debug("Finished testEditProduct.");
    }

    @Test
    void testDeleteProduct() throws Exception {
        logger.debug("Starting testDeleteProduct...");
        ProductRequest productRequest = getProductRequest();
        String productRequestString = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestString))
                .andExpect(status().isCreated());

        Product createdProduct = productRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/product/" + createdProduct.getId()))
                .andExpect(status().isOk());

        Assertions.assertEquals(0, productRepository.findAll().size());
        logger.debug("Finished testDeleteProduct.");
    }


    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("Apple")
                .productionCompany("Apple")
                .description("phone")
                .price(BigDecimal.TEN)
                .build();
    }

}