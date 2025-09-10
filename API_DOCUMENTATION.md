# Pet Care API Documentation

## Overview

This API provides pet management features including user registration/login, pet information management, and other functionalities.

## Base URL

```
http://localhost:8080/api
```

## 1. Authentication APIs

### 1.1 User Registration

**POST** `/auth/register`

**Request Body:**

```json
{
  "name": "Nguyễn Văn A",
  "email": "nguyenvana@email.com",
  "password": "password123",
  "phone": "0123456789",
  "address": "123 Đường ABC, Quận 1, TP.HCM",
  "role": "OWNER"
}
```

**Response:**

```json
{
  "userId": 1,
  "name": "Nguyễn Văn A",
  "email": "nguyenvana@email.com",
  "phone": "0123456789",
  "address": "123 Đường ABC, Quận 1, TP.HCM",
  "role": "OWNER",
  "token": "Registration successful",
  "message": null
}
```

1.2 User Login

**POST** `/auth/login`

**Request Body:**

```json
{
  "email": "nguyenvana@email.com",
  "password": "password123"
}
```

**Response:**

```json
{
  "userId": 1,
  "name": "Nguyễn Văn A",
  "email": "nguyenvana@email.com",
  "phone": "0123456789",
  "address": "123 Đường ABC, Quận 1, TP.HCM",
  "role": "OWNER",
  "token": "Login successful",
  "message": null
}
```

### 1.3 Lấy thông tin người dùng

**GET** `/auth/user/{userId}`

**Response:**

```json
{
  "userId": 1,
  "name": "Nguyễn Văn A",
  "email": "nguyenvana@email.com",
  "phone": "0123456789",
  "address": "123 Đường ABC, Quận 1, TP.HCM",
  "role": "OWNER",
  "token": "User found",
  "message": null
}
```

1.4 Update User Info
**PUT** `/auth/user/{userId}`

**Request Body:**

```json
{
  "name": "Nguyễn Văn A Updated",
  "email": "nguyenvana@email.com",
  "password": "newpassword123",
  "phone": "0987654321",
  "address": "456 Đường XYZ, Quận 2, TP.HCM",
  "role": "OWNER"
}
```

2. Pet Management APIs
   2.1 Create a New Pet

**POST** `/pets`

**Request Body:**

```json
{
  "ownerId": 1,
  "name": "Buddy",
  "species": "Chó",
  "breed": "Golden Retriever",
  "age": 3,
  "gender": "MALE",
  "photoUrl": "https://example.com/buddy.jpg"
}
```

**Response:**

```json
{
  "id": 1,
  "ownerId": 1,
  "name": "Buddy",
  "species": "Chó",
  "breed": "Golden Retriever",
  "age": 3,
  "gender": "MALE",
  "photoUrl": "https://example.com/buddy.jpg"
}
```

### 2.2 Create Multiple Pets

**POST** `/pets/batch`

**Request Body:**

```json
[
  {
    "ownerId": 1,
    "name": "Buddy",
    "species": "Chó",
    "breed": "Golden Retriever",
    "age": 3,
    "gender": "MALE",
    "photoUrl": "https://example.com/buddy.jpg"
  },
  {
    "ownerId": 1,
    "name": "Whiskers",
    "species": "Mèo",
    "breed": "Persian",
    "age": 2,
    "gender": "FEMALE",
    "photoUrl": "https://example.com/whiskers.jpg"
  }
]
```

###2.3 Get Pet by ID

**GET** `/pets/{id}`

**Response:**

```json
{
  "id": 1,
  "ownerId": 1,
  "name": "Buddy",
  "species": "Chó",
  "breed": "Golden Retriever",
  "age": 3,
  "gender": "MALE",
  "photoUrl": "https://example.com/buddy.jpg"
}
```

### 2.4 Update Pet Info

**PUT** `/pets/{id}`

**Request Body:**

```json
{
  "ownerId": 1,
  "name": "Buddy Updated",
  "species": "Chó",
  "breed": "Golden Retriever",
  "age": 4,
  "gender": "MALE",
  "photoUrl": "https://example.com/buddy-new.jpg"
}
```

### 2.5 Delete Pet

**DELETE** `/pets/{id}`

**Response:** `204 No Content`

### 2.6 Lấy danh sách thú cưng theo chủ sở hữu

**GET** `/pets/owner/{ownerId}`

**Response:**

```json
[
  {
    "id": 1,
    "ownerId": 1,
    "name": "Buddy",
    "species": "Chó",
    "breed": "Golden Retriever",
    "age": 3,
    "gender": "MALE",
    "photoUrl": "https://example.com/buddy.jpg"
  },
  {
    "id": 2,
    "ownerId": 1,
    "name": "Whiskers",
    "species": "Mèo",
    "breed": "Persian",
    "age": 2,
    "gender": "FEMALE",
    "photoUrl": "https://example.com/whiskers.jpg"
  }
]
```

### Get Pets by Species

**GET** `/pets/species/{species}`

### Get Pets by Breed

**GET** `/pets/breed/{breed}`

###2.9 Get All Pets

**GET** `/pets`

3. Implemented Features

-----User Registration and Login---------

-Owners can register with name, email, password, phone, address

-System enforces unique email

-Passwords encrypted with BCrypt

-Secure login with email and password

-Ability to update profile information

------Pet Profile Management----------

-Add, edit, delete, and view pet details

-Manage multiple pets per owner

-Pet info includes: name, species, breed, age, gender, photo

-API supports batch pet creation

-Search pets by species, breed
