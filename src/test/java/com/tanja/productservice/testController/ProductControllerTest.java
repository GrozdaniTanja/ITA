package com.tanja.productservice.testController;

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

import java.math.BigDecimal;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
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
    void testGetUserById() throws Exception {
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
    }

    @Test
    void testGetAllProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }


    @Test
    void testEditProduct() throws Exception {
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
    }

    @Test
    void testDeleteProduct() throws Exception {
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
    }

    @Test
    void testDeleteAllProducts() throws Exception {

        ProductRequest productRequest1 = getProductRequest();
        ProductRequest productRequest2 = getProductRequest();
        String productRequestString1 = objectMapper.writeValueAsString(productRequest1);
        String productRequestString2 = objectMapper.writeValueAsString(productRequest2);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestString1))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestString2))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/product"))
                .andExpect(status().isOk());

        // Ensure all products are deleted
        Assertions.assertEquals(0, productRepository.findAll().size());
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
