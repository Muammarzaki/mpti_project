## API Specification: Authentication Service

### Base URL
```
https://api.example.com/v1
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
- **400 Bad Request**
  ```json
  {
    "error": "Invalid input data."
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
- **401 Unauthorized**
  ```json
  {
    "error": "Invalid credentials."
  }
  ```

#### 3. Token Refresh

**Endpoint:** `/auth/refresh`  
**Method:** `POST`  
**Description:** Refreshes the JWT token.

**Request:**
```json
{
  "ref_token": "string"
}
```

**Response:**
- **200 OK**
  ```json
  {
    "token": "string"
  }
  ```
- **401 Unauthorized**
  ```json
  {
    "error": "Invalid token."
  }
  ```

#### 4. Access Protected Resource

**Endpoint:** `/protected/resource`  
**Method:** `GET`  
**Description:** Accesses a protected resource. Requires a valid JWT token.

**Request Headers:**
```
Authorization: Bearer <JWT token>
```

**Response:**
- **200 OK**
  ```json
  {
    "data": "protected data"
  }
  ```
- **401 Unauthorized**
  ```json
  {
    "error": "Invalid or missing token."
  }
  ```

### Error Handling

#### Common Errors

- **400 Bad Request**
  ```json
  {
    "error": "Bad request."
  }
  ```
- **401 Unauthorized**
  ```json
  {
    "error": "Unauthorized."
  }
  ```
- **403 Forbidden**
  ```json
  {
    "error": "Forbidden."
  }
  ```
- **404 Not Found**
  ```json
  {
    "error": "Not found."
  }
  ```
- **500 Internal Server Error**
  ```json
  {
    "error": "Internal server error."
  }
  ```

### Example Flow

1. **Register a User:** The client sends a `POST` request to `/auth/register` with the user's username, email, and password.
2. **Login a User:** The client sends a `POST` request to `/auth/login` with the user's email and password to receive a JWT token.
3. **Access Protected Resource:** The client sends a `GET` request to `/protected/resource` with the `Authorization` header set to `Bearer <JWT token>`.
4. **Refresh Token:** The client sends a `POST` request to `/auth/refresh` with the current JWT token to receive a new token.