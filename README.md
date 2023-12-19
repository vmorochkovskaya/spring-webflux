# Project Name

Spring-webflux and R2DBC progect.

## Table of Contents

- [Requirements](#requirements)
- [Getting Started](#getting-started)
- [Database Setup](#database-setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)

## Requirements

- Java 17
- Spring Boot 3
- MySQL Database
- Maven

## Getting Started

1. **Clone the repository:**

   ```bash
   git clone https://github.com/vmorochkovskaya/spring-webflux.git
   cd spring-webflux

2. **Install dependencies using Maven:**

```
mvn clean install
```

## Database Setup
   1. Create **testdb_spring_webflux** schema
   2. Create **post** table:
   ```
   use testdb_spring_webflux;
   CREATE TABLE post (post_id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255));
   ```
## Running the Application
```
mvn spring-boot:run
```

## API Endpoints
   #### 1. Create Post
- **Endpoint:** `POST /api/v1/post/{postname}`
- **Example:** `POST http://localhost:8080/api/v1/post/dsadd`
   #### 2. Search Posts
- **Endpoint:** `GET /api/v1/post?q=...`
   #### 3. Get All Posts And Save
- **Endpoint:** `GET /api/v1/posts`
   #### 4. Get All Posts And Save with backpressure
- **Endpoint:** `GET /api/v1/posts/back`

