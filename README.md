## Initial Setup

The initial commit on `master` was created using [Spring Initializr](https://start.spring.io/).

- **Build Tool:** Gradle (Groovy DSL)
- **Language:** Java
- **Spring Boot Version:** 3.5.9
- **Java Version:** 21

### Selected Starter Dependencies

- **Spring Reactive Web**
- **Validation**
- **Spring Configuration Processor**

## Docker Image

The application is available as a Docker image on Docker Hub:

- **Repository:** https://hub.docker.com/repository/docker/devilek66/phishing-link-checker/general

### Image Build

The Docker image is built using **Google Jib**.

### Pulling the Image

Below is an **example** of how to pull a specific image tag:

```bash
docker pull devilek66/phishing-link-checker:feature-JD-1-2decce6
```

To run the container and map it to a local port (e.g. 8080), use:

```bash
docker run -p 8080:8080 devilek66/phishing-link-checker:feature-JD-1-2decce6
```

After startup, the service will be available at:
```
http://localhost:8080
```

## Caching Strategy

To reduce the number of calls to a paid external service (Google Web Risk API), the application uses an in-memory cache based on Caffeine.

The cache is implemented at the service layer using Spring Cache with WebFlux and has the following characteristics:
- cache key: checked URL
- time-to-live (TTL): 10 hours
- maximum cache size: 10,000 entries
- AsyncCache mode enabled for better support of reactive types (Mono)

Additionally, Mono.cache() is used to:
- deduplicate concurrent subscriptions
- ensure that a single URL results in only one external API call

This approach:

- significantly reduces the number of costly external requests
- improves response latency
- protects the system against traffic bursts

### Future Improvement

As a next step, the cache should be migrated to Redis in order to:

- share cached data across multiple application instances
- avoid cache warm-up after restarts
- improve horizontal scalability and fault tolerance

## API Endpoints

### 1. Add or Update a Number with Active Subscription
```bash
curl --location --request POST "http://localhost:8080/subscriptions/691799769/activate"
```

### 2. Add or Update a Number with Deactivated Subscription
```bash
curl --location --request POST "http://localhost:8080/subscriptions/691799769/deactivate"
```

### 3. Check Subscription Status for a Number
```bash
curl --location --request GET "http://localhost:8080/subscriptions/691799769"
```
Note: If the number does not exist, this endpoint will return a 404.

### 4. Verify Messages Received by Users
This endpoint is used to check messages received by users, according to the task requirements. When running locally, a mock service simulates an external URL checking service.

```bash
curl --location --request POST "http://localhost:8080/verification" --header "Content-Type: application/json" --data-raw "{\"sender\":\"123123123\",\"recipient\":\"691799769\",\"message\":\"http://examp211le.com\"}"
```
### Example URLs and Risk Levels (Mock)
- LOW risk URL: http://examp1le.com
- CRITICAL risk URL: http://examp211le.com


## Assumptions & Possible Improvements

- **Current service handling via controllers**  
  At the moment, the service is handled through controllers. This part could be replaced with handlers for Kafka, RabbitMQ, or any other message queue system if needed.

- **No database integration**  
  Currently, there is no database to keep the implementation simple. A mock repository handles subscription states.  
  Consider whether subscription state is actually maintained by another service or an existing database. This mock can be replaced once the infrastructure details are known.

- **SMS commands ("STOP"/"START")**  
  The task mentioned handling SMS commands like "STOP" and "START". Currently, two separate endpoints are implemented.  
  If SMS commands were to be processed alongside other messages (e.g., via Kafka events), it would require clarification on which recipient these commands apply to, in order to handle them correctly.

- **Testing**  
  More tests could be added. Currently, tests cover the "most interesting" parts of the code and include an example with WireMock.

- **Controllers packaging**  
  Controllers could be moved into a common `web` package for better organization. For now, they are left in their current locations.