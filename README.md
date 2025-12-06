# RideShare Backend API

A RESTful API for a mini Ride Sharing application built with **Spring Boot**, **MongoDB**, and **JWT Authentication**.
This project allows users to register, book rides, and allows drivers to accept and complete them.

---

## Technologies Used

* **Java 17+**
* **Spring Boot 3.x**
* **Spring Data MongoDB**
* **Spring Security & JWT** (JSON Web Tokens)
* **Maven**

---

## Setup & Installation

1. **Clone the repository:**
   ```
   git clone https://github.com/akshat-code21/ride-sharing-app-springboot.git
   ```

2. **Configure Database:**
   Ensure MongoDB is running locally on port `27017`.

3. **Run the Application:**
   ```
    mvn spring-boot:run
    ```
   The server will start on `http://localhost:8081`.

---

## API Endpoints

### Authentication (Public)

| Method | Endpoint | Description | Payload Example |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Register a new User or Driver | `{"username": "john", "password": "123", "role": "ROLE_USER"}` |
| `POST` | `/api/auth/login` | Login to get JWT Token | `{"username": "john", "password": "123"}` |

### Passenger Endpoints (Requires `ROLE_USER`)

| Method | Endpoint | Description | Payload Example |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/rides` | Request a new ride | `{"pickupLocation": "A", "dropLocation": "B"}` |
| `GET` | `/api/v1/user/rides` | View ride history | *None* |

### Driver Endpoints (Requires `ROLE_DRIVER`)

| Method | Endpoint | Description | Payload Example |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/driver/rides/requests` | View all pending rides | *None* |
| `POST` | `/api/v1/driver/rides/{id}/accept` | Accept a requested ride | *None* |

### Shared Endpoints (Requires `ROLE_DRIVER` or `ROLE_USER`)

| Method | Endpoint | Description | Payload Example |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/rides/{id}/complete` | Mark a ride as Completed | *None* |

---

## Testing with CURL

Use the commands below to test the full flow in your terminal.

### 1. Register a User
```

curl -X POST http://localhost:8081/api/auth/register \
-H "Content-Type: application/json" \
-d '{"username":"john","password":"password123","role":"ROLE_USER"}'
```
### 2. Login (Copy the token from response)

```
curl -X POST http://localhost:8081/api/auth/login \
-H "Content-Type: application/json" \
-d '{"username":"john","password":"password123"}'
```


### 3. Create a Ride
Replace `<TOKEN>` with the token received from login.
```

curl -X POST http://localhost:8081/api/v1/rides \
-H "Authorization: Bearer <TOKEN>" \
-H "Content-Type: application/json" \
-d '{"pickupLocation":"Koramangala","dropLocation":"Indiranagar"}'

```

---
