# REST API Template

A level 3 maturity model REST API template for user management, following the Richardson Maturity Model and SOLID principles. This template serves as a foundation for building robust and scalable RESTful APIs.

## Features

- **HATEOAS Support**: Hypermedia as the Engine of Application State for better API discoverability
- **Content Negotiation**: Support for both JSON and CSV response formats
- **Pagination**: Efficient handling of large datasets with pagination
- **Proper HTTP Status Codes**: Following REST best practices
- **Comprehensive Error Handling**: Detailed error responses with appropriate status codes
- **Swagger Documentation**: Interactive API documentation
- **Docker Support**: Easy deployment with Docker Compose

## Technologies

- Java 21
- Spring Boot
- Spring HATEOAS
- Spring Data JPA
- PostgreSQL
- Docker & Docker Compose
- OpenCSV
- Swagger/OpenAPI

## Getting Started

### Prerequisites

- Java 21 or higher
- Docker and Docker Compose
- Maven (optional, if not using Docker)

### Running with Docker Compose

1. Clone the repository:
   ```bash
   git clone https://github.com/fabiornt/rest-template.git
   cd rest-template
   ```

2. Build and run the application using Docker Compose:
   ```bash
   docker-compose up -d
   ```

   This will start both the PostgreSQL database and the Spring Boot application.

3. The API will be available at:
   ```
   http://localhost:8080/api
   ```

### Running Locally (without Docker)

1. Clone the repository:
   ```bash
   git clone https://github.com/fabiornt/rest-template.git
   cd rest-template
   ```

2. Configure your database connection in `src/main/resources/application.properties`

3. Build and run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## API Documentation

### Swagger UI

The API documentation is available through Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

This interactive documentation allows you to:
- Explore all available endpoints
- View request/response models
- Test the API directly from the browser

### OpenAPI Specification

The OpenAPI specification is available at:
```
http://localhost:8080/api-docs
```

### Quick Testing with HTTP File

The repository includes a `rest-template-api.http` file that can be used with REST Client extensions in VS Code or IntelliJ IDEA for quick testing.

To use it:
1. Open the file in your IDE
2. Click on the "Send Request" link above each request
3. View the response directly in the editor

## API Endpoints

### User Management

- `GET /api/users` - Get all users (supports pagination and content negotiation)
- `GET /api/users/{id}` - Get a specific user by ID
- `POST /api/users` - Create a new user
- `PUT /api/users/{id}` - Update a user
- `PATCH /api/users/{id}` - Partially update a user
- `DELETE /api/users/{id}` - Delete a user

### Pagination

The API supports pagination for collection endpoints:

```
GET /api/users?page=0&size=10
```

Parameters:
- `page`: Page number (0-based, default: 0)
- `size`: Page size (default: 10)

### Content Negotiation

The API supports both JSON and CSV formats:

For JSON (default):
```
GET /api/users
Accept: application/json
```

For CSV:
```
GET /api/users
Accept: text/csv
```

## Error Handling

The API provides detailed error responses with appropriate HTTP status codes:

- `400 Bad Request` - Invalid request syntax
- `404 Not Found` - Resource not found
- `409 Conflict` - Resource conflict (e.g., duplicate email)
- `422 Unprocessable Entity` - Validation errors
- `500 Internal Server Error` - Server-side errors

## CORS Support

The API includes CORS support, allowing it to be accessed from different origins. The CORS configuration can be customized in the `application.properties` file.

## Project Structure

```
src/main/java/com/fabiornt/rest_template/
├── config/                  # Configuration classes
├── controller/              # REST controllers
├── domain/                  # Domain model
│   ├── entity/              # JPA entities
│   └── model/               # Response models with HATEOAS
├── exception/               # Exception handling
├── http/                    # HTTP response wrappers
├── repository/              # Data repositories
├── service/                 # Business logic
└── util/                    # Utility classes
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring HATEOAS](https://spring.io/projects/spring-hateoas)
- [Richardson Maturity Model](https://martinfowler.com/articles/richardsonMaturityModel.html)
- [OpenCSV](http://opencsv.sourceforge.net/)
- [Swagger/OpenAPI](https://swagger.io/specification/)
