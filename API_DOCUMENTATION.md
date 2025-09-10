# Pet Care API Documentation

## Tổng quan

API này cung cấp các tính năng quản lý thú cưng bao gồm đăng ký/đăng nhập người dùng, quản lý thông tin thú cưng, và các tính năng khác.

## Base URL

```
http://localhost:8080/api
```

## 1. Authentication APIs

### 1.1 Đăng ký người dùng

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

### 1.2 Đăng nhập

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

### 1.4 Cập nhật thông tin người dùng

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

## 2. Pet Management APIs

### 2.1 Tạo thú cưng mới

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

### 2.2 Tạo nhiều thú cưng cùng lúc

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

### 2.3 Lấy thông tin thú cưng theo ID

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

### 2.4 Cập nhật thông tin thú cưng

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

### 2.5 Xóa thú cưng

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

### 2.7 Lấy danh sách thú cưng theo loài

**GET** `/pets/species/{species}`

### 2.8 Lấy danh sách thú cưng theo giống

**GET** `/pets/breed/{breed}`

### 2.9 Lấy tất cả thú cưng

**GET** `/pets`

## 3. Các tính năng đã implement

### ✅ Đăng ký và Đăng nhập Người dùng

- Chủ vật nuôi có thể đăng ký với thông tin: tên, email, mật khẩu, số điện thoại, địa chỉ
- Hệ thống xác thực email duy nhất
- Mã hóa mật khẩu bằng BCrypt
- Đăng nhập an toàn với email và mật khẩu
- Cập nhật thông tin cá nhân

### ✅ Quản lý Hồ sơ Thú cưng

- Thêm, sửa, xóa, xem thông tin thú cưng
- Quản lý nhiều thú cưng cho một chủ sở hữu
- Thông tin thú cưng bao gồm: tên, loài, giống, tuổi, giới tính, ảnh
- API hỗ trợ tạo nhiều thú cưng cùng lúc
- Tìm kiếm thú cưng theo loài, giống

## 4. Cấu trúc Database

### Bảng `owners`

- `id` (Primary Key)
- `name` (Tên chủ sở hữu)
- `email` (Email - unique)
- `password_hash` (Mật khẩu đã mã hóa)
- `phone` (Số điện thoại)
- `address` (Địa chỉ)
- `role` (Vai trò: OWNER, VET, SHELTER, ADMIN)

### Bảng `pets`

- `id` (Primary Key)
- `owner_id` (Foreign Key → owners)
- `name` (Tên thú cưng)
- `species` (Loài)
- `breed` (Giống)
- `age` (Tuổi)
- `gender` (Giới tính: MALE, FEMALE, UNKNOWN)
- `photo_url` (URL ảnh)

## 5. Cách chạy ứng dụng

1. **Cài đặt MySQL:**

   - Tạo database `petcare_db`
   - Cập nhật thông tin kết nối trong `application.properties`

2. **Chạy ứng dụng:**

   ```bash
   mvn spring-boot:run
   ```

3. **Truy cập API:**
   - Base URL: `http://localhost:8080/api`
   - Swagger UI: `http://localhost:8080/swagger-ui.html` (nếu có)

## 6. Ví dụ sử dụng với cURL

### Đăng ký người dùng:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Nguyễn Văn A",
    "email": "nguyenvana@email.com",
    "password": "password123",
    "phone": "0123456789",
    "address": "123 Đường ABC, Quận 1, TP.HCM"
  }'
```

### Tạo thú cưng:

```bash
curl -X POST http://localhost:8080/api/pets \
  -H "Content-Type: application/json" \
  -d '{
    "ownerId": 1,
    "name": "Buddy",
    "species": "Chó",
    "breed": "Golden Retriever",
    "age": 3,
    "gender": "MALE",
    "photoUrl": "https://example.com/buddy.jpg"
  }'
```

## 7. Lưu ý

- Tất cả API đều hỗ trợ CORS
- Validation được áp dụng cho tất cả input
- Mật khẩu được mã hóa bằng BCrypt
- Database sẽ tự động tạo bảng khi chạy ứng dụng lần đầu
- Có thể mở rộng thêm JWT authentication trong tương lai

