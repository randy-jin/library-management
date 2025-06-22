# 📚 Library Management API

A Spring Boot RESTful API for managing a library of books — complete with containerization, Kubernetes deployment, and an efficient pair-sum algorithm implementation.

---

## 📑 Table of Contents
- [Project Overview](#project-overview)
- [Task 1: REST API](#coding-task-1-rest-api)
- [Task 2: Kubernetes Configuration](#coding-task-2-kubernetes-configuration)
- [Task 3: Pair Sum Algorithm](#coding-task-3-pair-sum-algorithm)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Technologies Used](#technologies-used)
- [Related Links](#related-links)

---

## 📌 Project Overview

This project demonstrates backend development capabilities through:

- ✅ RESTful API development with Spring Boot
- ✅ Docker containerization and Kubernetes orchestration
- ✅ Efficient algorithm implementation
- ✅ Unit testing and input validation
- ✅ Exception handling and clean architecture

---

## ⚙️ Coding Task 1: REST API

### 🔗 Endpoints

| Method | Endpoint        | Description                | Status Codes                     |
|--------|------------------|----------------------------|----------------------------------|
| POST   | `/books`         | Add a new book             | `201`, `400`                     |
| GET    | `/books`         | Retrieve all books         | `200`                            |
| GET    | `/books/{id}`    | Retrieve a specific book   | `200`, `404`                     |
| PUT    | `/books/{id}`    | Update a book              | `200`, `400`, `404`              |
| DELETE | `/books/{id}`    | Delete a book              | `204`, `404`                     |

### 🛠 Implementation Highlights

- Spring Boot 3.x (Java 17)
- In-memory storage using `ConcurrentHashMap`
- Input validation (e.g., non-negative prices)
- Global exception handler with meaningful error responses
- Unit tests for `POST` and `GET /books/{id}`

---

## ☸️ Coding Task 2: Kubernetes Configuration

### 🔧 Components

- **Dockerfile**: Multi-stage image for efficiency
- **Deployment**: 3 replicas for high availability
- **Service**: Internal `ClusterIP` access
- **Ingress**: Exposes the app via `library-api.local`
- **ConfigMap**: Injects environment variable `APP_ENV=production`

### 📁 Deployment Files

```bash
kubernetes/
├── configmap.yaml      # APP_ENV=production
├── deployment.yaml     # 3-replica deployment
├── service.yaml        # ClusterIP service
└── ingress.yaml        # Ingress routing
```

### 📊 Deployment Status (Sample Output)

```bash
kubectl get pods

NAME                                  READY   STATUS    RESTARTS   AGE
library-management-7c9bd9d588-btrzq   1/1     Running   0          3m31s
...

kubectl get ingress

NAME                         CLASS   HOSTS               ADDRESS   PORTS   AGE
library-management-ingress   nginx   library-api.local             80      9h
```

---

## 🧠 Coding Task 3: Pair Sum Algorithm

### ✅ Problem

Find all pairs of integers in an array whose sum equals a given target.

### 💡 Solution Approaches

1. **HashMap (O(n) time)**
   - Fast, single-pass solution using constant-time lookups
   - Avoids duplicate pairs

2. **Two-Pointer (O(n log n) time)**
   - Sort first, then scan with two pointers
   - Memory-efficient

### 🔍 Example

```java
Input:  [2, 4, 3, 7, 1, 5]
Target: 6
Output: [(2, 4), (1, 5)]
```

> 📝 **Note:** While the original example mentioned `(3, 3)`, it was excluded since only one `3` exists in the input array — forming this pair would require two instances.

---

## 🧾 Project Structure

```bash
library-management/
├── src/
│   ├── main/
│   │   ├── java/com/library/
│   │   │   ├── controller/               # REST endpoints
│   │   │   ├── model/                    # Book entity
│   │   │   ├── service/                  # Business logic
│   │   │   ├── exception/                # Global error handling
│   │   │   ├── PairSumSolution.java      # Task 3 solution
│   │   │   └── LibraryManagementApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/library/
│           ├── controller/
│           └── PairSumSolutionTest.java
├── kubernetes/
├── Dockerfile
├── pom.xml
└── README.md
```

---

## 🚀 Getting Started

### 🔧 Prerequisites

- Java 17+
- Maven 3.6+
- Docker
- Kubernetes (e.g., minikube, kind, or a remote cluster)

### 🔍 Local Development

```bash
# Clone the repo
git clone https://github.com/randy-jin/library-management.git
cd library-management

# Build and run
mvn clean package
java -jar target/library-management.jar
```

### 🐳 Docker Build and Run

```bash
docker build -t randyjin/library-management:latest
docker run -p 8080:8080 randyjin/library-management:latest
```

### ☸️ Kubernetes Deployment

```bash
kubectl apply -f kubernetes/configmap.yaml
kubectl apply -f kubernetes/deployment.yaml
kubectl apply -f kubernetes/service.yaml
kubectl apply -f kubernetes/ingress.yaml
```

---

## 📘 API Documentation

### ➕ Add a Book

```http
POST /books
Content-Type: application/json

{
  "title": "Spring Boot in Action",
  "author": "Craig Walls",
  "price": 39.99
}
```

**Response:**
```json
{
  "id": 1,
  "title": "Spring Boot in Action",
  "author": "Craig Walls",
  "price": 39.99
}
```

### 📚 Get All Books

```http
GET /books
```

**Response:**
```json
[
  {
    "id": 1,
    "title": "Spring Boot in Action",
    "author": "Craig Walls",
    "price": 39.99
  },
  {
    "id": 2,
    "title": "Effective Java",
    "author": "Joshua Bloch",
    "price": 45.50
  }
]
```

---

## ✅ Testing

Run all tests with:

```bash
mvn test
```

### 🧪 Includes:
- Controller unit tests (POST, GET)
- Validation rules
- Algorithm edge cases

---

## 🧰 Technologies Used

- **Java 17**, **Spring Boot 3.x**
- **Maven** for build
- **JUnit 5**, **Spring Test** for unit testing
- **Docker** for containerization
- **Kubernetes** for orchestration
- **H2 Database** for in-memory persistence

---

## 🔗 Related Links

- 📁 [GitHub Repository](https://github.com/randy-jin/library-management)
- 👤 [LinkedIn - Randy Jin](https://www.linkedin.com/in/randy-jin-6b037523a/)