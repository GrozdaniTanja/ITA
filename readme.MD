# Product Service

## Overview
The OnlineMarketPlace Product Service is a microservice within the OnlineMarketPlace project. Built using Spring Boot and MongoDB Atlas, it offers RESTful API endpoints to facilitate CRUD (Create, Read, Update, Delete) operations for managing products.
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
3. Run app:
   - Option 1: Run with Maven
    ```./mvnw spring-boot:run  ```  
   - Option 2: Run with Docker Compose(Ensure Docker is installed on your system)
   ``` docker-compose up ```
## Swagger UI 
http://localhost:8080/swagger-ui/index.html#.
