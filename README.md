# 🚀 Campus Microservices

A Spring Boot microservices demo for managing **departments** and **students**, with service discovery, API routing, and inter-service communication.

## Architecture

```text
                         ┌──────────────────────┐
                         │     API Gateway      │
                         │       :9090          │
                         └──────────┬───────────┘
                                    │ Eureka / LB
                 ┌──────────────────┴──────────────────┐
                 ▼                                     ▼
       ┌────────────────────┐                ┌────────────────────┐
       │ Department Service │◄──── Feign ────│  Student Service   │
       │       :8081        │                │       :8080        │
       └─────────┬──────────┘                └─────────┬──────────┘
                 │                                     │
          department_db                            students_db

                 ┌──────────────────────────────┐
                 │       Eureka Registry        │
                 │           :8761              │
                 └──────────────────────────────┘
```

**Technology:** Java 21 · Spring Boot · Spring Cloud Netflix Eureka · Spring Cloud Gateway · OpenFeign · Spring Data JPA · MySQL · Maven

## Services

| Service | Port | Responsibility |
|---|---:|---|
| `service-registry` | `8761` | Registers services and provides discovery |
| `api-gateway` | `9090` | Routes external requests to services |
| `department-service` | `8081` | Department CRUD REST API |
| `student-service` | `8080` | Student REST API and department lookup |

## Run locally

**Prerequisites:** JDK 21, MySQL, and Maven (or the included Maven Wrapper).

1. Create the MySQL databases:
   ```sql
   CREATE DATABASE department_db;
   CREATE DATABASE students_db;
   ```
2. Configure each service's database username/password using environment variables or your local, uncommitted configuration. Do not commit credentials.
3. Start the applications in this order, each in its own terminal:

   | Start | Folder | Windows command |
   |---|---|---|
   | 1. Eureka | `service-registry/service-registry` | `.\mvnw.cmd spring-boot:run` |
   | 2. Department | `department-service` | `.\mvnw.cmd spring-boot:run` |
   | 3. Student | `student-service/student-service` | `.\mvnw.cmd spring-boot:run` |
   | 4. Gateway | `api-gateway/api-gateway` | `.\mvnw.cmd spring-boot:run` |

   Run each command from the listed service folder. On macOS/Linux, use `./mvnw spring-boot:run`.

4. Open the Eureka dashboard at **http://localhost:8761** and check that the client services register.

> The gateway and clients use `http://localhost:8761/eureka/` for local discovery. If you change ports or run services in Docker, update the relevant configuration and use reachable service hostnames.

## API quick reference

Requests can be sent directly to a service or through the gateway.

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/departs` | List departments |
| `GET` | `/departs/{id}` | Get a department |
| `POST` | `/departs` | Create a department |
| `PUT` | `/departs/{id}` | Update a department |
| `DELETE` | `/departs/{id}` | Delete a department |
| `GET` | `/students` | List students |
| `POST` | `/students` | Create a student |
| `GET` | `/students/{id}` | Get a student |
| `GET` | `/students/details/{id}` | Get student with department details |

**Gateway examples** (configured routes): `http://localhost:9090/departs/**` and `http://localhost:9090/students/**`.

### Example request bodies

Create a department (`POST /departs`):
```json
{
  "departmentName": "Computer Science",
  "hod": "Dr. Kumar",
  "location": "Block A",
  "isNbaAccredited": true
}
```

Create a student (`POST /students`):
```json
{
  "name": "Ananya",
  "email": "ananya@example.com",
  "departmentId": 1
}
```

The student service uses **OpenFeign** to look up the department through Eureka (`DEPARTMENT-SERVICE`) when validating/returning student details.

## Configuration notes

- Service names and ports are set in each service's `src/main/resources/application.properties`.
- Department and student persistence use MySQL with JPA/Hibernate (`ddl-auto=update` in the current local configuration).
- Keep database credentials outside Git (environment variables or an ignored local profile).
- The project currently has independently generated Maven projects and Spring Boot/Spring Cloud version declarations per service. Keep each pair compatible when upgrading.
- Gateway routes currently target the Eureka service IDs `DEPARTMENT-SERVICE` and `STUDENT-SERVICE`.

---
**Learning focus:** service registration & discovery · API Gateway routing · client-side load balancing · OpenFeign communication · REST CRUD · JPA persistence.
