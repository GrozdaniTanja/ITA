# Product Service

## Overview
This project is a product service. One of microservice of OnlineMarketPlace project. It is built using Spring Boot and MongoDB Atlas and provides RESTful API endpoints for CRUD operations to manage products.

## Endpoints
- POST /api/product: Create a new product.
- GET /api/product: Retrieve all products.
- GET /api/product/{id}: Retrieve a product by its ID.
- PUT /api/product/{id}: Update a product by its ID.
- DELETE /api/product: Delete all products.
- DELETE /api/product/{id}: Delete a product by its ID.


## Getting Started
1. Clone the repository.
2. Configure MongoDB Atlas in application.properties.
   - Option 1: Run with Maven
    ```./mvnw spring-boot:run  ```  
   - Option 2: Run with Docker Compose(Ensure Docker is installed on your system)
   ``` docker-compose up ```
## Swagger UI 
http://localhost:8080/swagger-ui/index.html#.
