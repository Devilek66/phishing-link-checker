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