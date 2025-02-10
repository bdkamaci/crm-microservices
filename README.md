# CRM Microservices Project

## Table of Contents
- [Introduction](#introduction)
- [Technologies Used](#technologies-used)
- [Architecture Overview](#architecture-overview)
- [Microservices](#microservices)
- [API Documentation](#api-documentation)
- [Setup and Installation](#setup-and-installation)
- [Docker and Deployment](#docker-and-deployment)
- [Security](#security)
- [Contributing](#contributing)
- [License](#license)

---

## Introduction
This project is a Customer Relationship Management (CRM) system built using microservices architecture. It provides authentication, customer management, sales processing, and user management functionalities.

## Technologies Used
- **Java 21**
- **Spring Boot** (Spring Cloud Gateway, Spring Data JPA, Spring Security, Feign Clients)
- **PostgreSQL** (Databases)
- **Eureka Discovery Server**
- **Feign Clients** (Inter-service communication)
- **Swagger API Documentation**
- **Docker & Docker Compose**
- **JWT Authentication**

## Architecture Overview
This project follows a microservices-based architecture where each service is independent and communicates via REST APIs. The main components include:
- **API Gateway** (Handles authentication and routing requests to appropriate microservices)
- **Authentication Service** (Manages user authentication and token generation)
- **User Service** (Handles user data and permissions)
- **Customer Service** (Manages customer records and interactions)
- **Sales Service** (Processes and tracks sales transactions)
- **Eureka Server** (Service discovery for microservices)

## Microservices
### 1. API Gateway
- Routes requests to appropriate microservices.
- Implements JWT authentication.

### 2. Authentication Service
- User authentication and role management.
- Generates and validates JWT tokens.

### 3. User Service
- Manages users and their permissions.
- Interacts with Authentication Service for validation.

### 4. Customer Service
- Manages customer data.
- Uses PostgreSQL for data storage.

### 5. Sales Service
- Handles sales transactions and order processing.
- Interacts with Customer Service.

## API Documentation
The API endpoints are documented using **Swagger** and can be accessed at:
```
http://localhost:8080/swagger-ui.html
```
API documentation is centralized and accessible through the API Gateway's Swagger UI.

## Setup and Installation
### Prerequisites
- Install **JDK 21**
- Install **Docker & Docker Compose**
- Install **PostgreSQL**
- Clone the repository:
  ```sh
  git clone https://github.com/bdkamaci/crm-microservices.git
  cd crm-microservices
  ```

### Running the Application
#### 1. Start the Eureka Server
```sh
cd eureka-server
mvn spring-boot:run
```

#### 2. Start the Microservices
Run each service separately:
```sh
cd api-gateway && mvn spring-boot:run
cd auth-service && mvn spring-boot:run
cd user-service && mvn spring-boot:run
cd customer-service && mvn spring-boot:run
cd sales-service && mvn spring-boot:run
```

## Docker and Deployment
To run the entire system using **Docker Compose**, use the following command:
```sh
docker-compose up --build
```
The **docker-compose.yml** file includes:
- API Gateway
- Authentication Service
- Customer Service
- Sales Service
- User Service
- Eureka Server
- PostgreSQL databases

## Security
- Uses **JWT Authentication** to secure API endpoints.
- API Gateway filters unauthorized requests.
- User roles are managed via Authentication Service.

## Contributing
Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a new branch.
3. Make your changes and commit.
4. Push the changes and create a pull request.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

