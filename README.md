# Microservice Architecture

This project is a key component of the "Software Design and Frameworks for Networked Systems" course. It's designed as a microservices architecture to efficiently handle and present sensor data via a RESTful web service.

## Repository Structure

This project is organized as a monorepo, containing multiple distinct services under a single repository. 
Each microservice, with its unique functionalities and responsibilities, is located in its own dedicated module.
This structure not only simplifies navigation but also enhances modularity and maintainability.

## Getting Started

To begin with this project, ensure the following system requirements are met:

- Docker must be installed and running on your machine. This is crucial for containerization and ensuring a consistent environment across different setups.
- Maven is needed for building the project.

Once Docker is set up, follow these steps:

1. Clone the project repository to your local machine.
2. Build the entire project using Maven. In the project's root directory, execute the command: `mvn clean install`. This will compile the source code, run tests, and package the application.
3. To launch the services, execute the `start_backend.bat` file. This script initiates all the necessary Docker containers and sets up the microservices environment.
4. When you need to stop the services, simply run the `stop_backend.bat` file. This will gracefully shut down all the running containers and clean up the environment.

## Usage

For general usage:

- **API Gateway as the Central Entrypoint**: The project is configured with port `8080` serving as the general entry point. This port is associated with the API Gateway, directing requests to the appropriate microservices.

- **Swagger UI for Endpoint Documentation**: Explore the REST endpoints in detail by visiting `localhost:8080/swagger` in your browser.

- **Accessing REST API Endpoints**: For direct interaction with the `rest-api` endpoints, use the `rest-api` prefix in your endpoint URLs. For example, `localhost:8080/rest-api/sensors` would be the correct format to access the `sensors` endpoint.

---