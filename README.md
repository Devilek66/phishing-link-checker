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