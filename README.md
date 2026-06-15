# Insurance Premium Calculation Microservices

## Overview

This project is a Java Spring Boot microservice application for calculating insurance premiums and managing insurance applications.

The system consists of two independent microservices:

* **calculation-service** - calculates insurance premiums
* **application-service** - manages insurance applications and persists data

The project was developed as part of a technical interview assignment with a focus on:

* Simplicity
* Testability
* Maintainability
* Clean Architecture
* Microservice Design

---

# Architecture

```text
Client
   |
   | HTTP/REST
   v
Application Service (8080)
   |
   | REST API
   v
Calculation Service (8081)
   |
   v
Postcodes CSV

Application Service
   |
   v
PostgreSQL (Supabase)
```

---

# Services

## 1. Calculation Service

Port:

```text
8081
```

Responsibilities:

* Calculate insurance premium
* Resolve region from German postcode
* Provide HTTP API for third-party integrations
* Read postcode information from `postcodes.csv`

### Endpoints

#### Calculate Premium

```http
POST /api/premium/calculate
```

#### Health Check

```http
GET /api/premium/health
```

---

## 2. Application Service

Port:

```text
8080
```

Responsibilities:

* Create insurance applications
* Persist applications in PostgreSQL
* Communicate with calculation-service
* Provide CRUD APIs

### Endpoints

#### Create Application

```http
POST /api/applications
```

#### Get All Applications

```http
GET /api/applications
```

#### Get Application By ID

```http
GET /api/applications/{id}
```

#### Health Check

```http
GET /api/applications/health
```

---

# Premium Calculation Formula

```text
Premium =
Mileage Factor
× Vehicle Type Factor
× Region Factor
× 100
```

## Mileage Factors

| Annual Mileage     | Factor |
| ------------------ | ------ |
| 0 – 5,000 km       | 0.5    |
| 5,001 – 10,000 km  | 1.0    |
| 10,001 – 20,000 km | 1.5    |
| > 20,000 km        | 2.0    |

## Vehicle Type Factors

| Vehicle    | Factor |
| ---------- | ------ |
| MOTORCYCLE | 0.5    |
| CAR        | 1.0    |
| VAN        | 1.5    |
| TRUCK      | 2.0    |

## Region Factors

| Region              | Factor |
| ------------------- | ------ |
| Bayern              | 1.3    |
| Nordrhein-Westfalen | 1.3    |
| Berlin              | 1.5    |
| Hamburg             | 1.4    |
| Baden-Württemberg   | 1.2    |
| Hessen              | 1.2    |
| Others              | 1.0    |

---

# Technology Stack

* Java 17
* Spring Boot 3.2.5
* Spring Data JPA
* Maven
* PostgreSQL (Supabase)
* Docker
* Docker Compose
* Swagger/OpenAPI
* JUnit 5
* MockMvc

---

# Why PostgreSQL?

PostgreSQL was chosen because insurance applications contain structured relational data that requires transactional consistency, reliability, and strong SQL support. PostgreSQL integrates seamlessly with Spring Data JPA and is widely used in production systems.

---

# Running the Project

## Requirements

* Java 17
* Maven 3.9+
* Docker Desktop
* Git

---

## Clone Repository

```bash
git clone https://github.com/yourusername/insurance-microservices.git
cd insurance-microservices
```

---

## Environment Variables

Create:

```text
application-service/.env
```

Example:

```env
DB_PASSWORD=your_password
```

---

## Start Application

```bash
cd .\calculation-service
docker compose up --build

cd .\application-service
docker compose up --build
```

---

# Swagger

Application Service:

```text
http://localhost:8080/swagger-ui.html
```

Calculation Service:

```text
http://localhost:8081/swagger-ui.html
```

---

# Tests

Implemented tests:

* ApplicationServiceTest
* ApplicationControllerTest

The project uses JUnit and MockMvc to verify:

* Service layer logic
* Controller endpoints
* Validation
* Error handling

---

# Future Improvements

* Integration Tests with Testcontainers
* Circuit Breaker using Resilience4j
* Centralized Logging
* API Gateway
* Authentication with Spring Security/JWT
* Frontend UI (React)

---

# Author

Sudin
Technical Informatics Student
