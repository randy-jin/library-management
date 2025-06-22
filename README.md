# Library Management System

A Spring Boot application that provides a RESTful API for library management, deployed with Kubernetes.

## Coding Task 1: REST API

### Endpoints

- `POST /books`: Add a new book with validation for non-negative price
- `GET /books`: Retrieve all books
- `GET /books/{id}`: Retrieve a specific book by ID (404 if not found)
- `PUT /books/{id}`: Update a book with validation (404 if not found)
- `DELETE /books/{id}`: Delete a book by ID (404 if not found)

### Implementation Details

- Spring Boot 3.x with Java 17
- ConcurrentHashMap<Long, Book> for persistence
- Exception handling with meaningful error messages
- Unit tests for POST and GET endpoints

## Coding Task 2: Kubernetes Configuration

### Components

- **Dockerfile**: Multi-stage build for optimized container image
- **Deployment**: 3 replicas for high availability
- **Service**: ClusterIP service for internal communication
- **Ingress**: External access via library-api.local
- **ConfigMap**: Environment variable injection

### Deployment Files

- `kubernetes/deployment.yaml`: 3-replica deployment
- `kubernetes/service.yaml`: Service configuration
- `kubernetes/ingress.yaml`: Ingress routing
- `kubernetes/configmap.yaml`: Environment variables

### Environment Configuration

The application reads the `APP_ENV` environment variable:
- Set to "prod" for production environments
- Configurable via Kubernetes ConfigMap

## Coding Task 3: Pair Sum Algorithm

Implementation of an algorithm to find all pairs in an array that sum to a target value.

### Approaches

1. **HashMap Approach (O(n) time complexity)**
    - Single pass through the array
    - Uses HashMap for O(1) lookups
    - Avoids duplicates with a HashSet

2. **Two-Pointer Approach (O(n log n) time complexity)**
    - Sorts the array first
    - Uses two pointers to find pairs
    - Avoids duplicates with pointer movement

### Example
Input: [2, 4, 3, 7, 1, 5], Target: 6 Output: [(2, 4), (1, 5)]

## Getting Started

### Prerequisites

- Java 17+
- Maven
- Docker
- Kubernetes cluster

### Local Development

```bash
# Build
mvn clean package

# Run
java -jar target/library-management.jar
```

### Kubernetes Deployment
#### Apply Kubernetes resources
```bash
kubectl apply -f kubernetes/
```