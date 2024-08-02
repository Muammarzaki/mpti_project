## API Specification: Authentication Service

### Base URL

```
https://api.example.com
```

### Endpoints

#### 1. User Registration

**Endpoint:** `/auth/register`  
**Method:** `POST`  
**Description:** Registers a new user.

**Request:**

```json
{
  "username": "string",
  "email": "string",
  "password": "string"
}
```

**Response:**

- **201 Created**
  ```json
  {
    "message": "User registered successfully."
  }
  ```
- **409 Conflict**
  ```json
  {
    "error": "User already exists."
  }
  ```

#### 2. User Login

**Endpoint:** `/auth/login`  
**Method:** `POST`  
**Description:** Authenticates a user and returns a JWT token.

**Request:**

```json
{
  "email": "string",
  "password": "string"
}
```

**Response:**

- **200 OK**
  ```json
  {
    "acc_token": "string"
  }
  ```

#### 3. JWK 

**Endpoint:** `/.well-know/jwks.json`  
**Method:** `GET`  
**Description:** KEY for verify the token.


**Response:**

- **200 OK**
  ```json
   {
      "kty": "RSA",
      "kid": "1234",
      "use": "sig",
      "alg": "RS256",
      "n": "0vx7agoebGcQSuuPiLJXZptN3cRR6mSkVPbGHcxZSMY1lmxtcP6LbUlbEFAHAAoxczDPjz4m8Zbcj53kRG4FPA1UcuY3NWWdUQPLr5xyMO5StzLJ3b8S5OqLF0Xjjjk9U6c2A_6m8Gn8EbS3iJIBg-8T_5nlD4zcmOZXmDrD4H5QhZnF0uN_t9QdO2_5DOwrgwz_tK-1kRXzzCRksRk7GvJQF_r5K_S5PhLa0E3tMiWjglqW1m_pH0_95qJgV3bTlK2dVDbTjrZ1SHiJ7HAFf3mIGtCbuPZBYbO5q2pU6EqJw3-bPbLkDW3zBQKL7C4xRXm6ZpJ9sU2J36dsYY0_splLecQ",
      "e": "AQAB"
    }
  ```

### Example Flow

1. **Register a User:** The client sends a `POST` request to `/auth/register` with the user's username, email, and
   password.
2. **Login a User:** The client sends a `POST` request to `/auth/login` with the user's email and password to receive a
   JWT token.