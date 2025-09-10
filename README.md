## Pet Care - Danh sách API

Dưới đây là danh sách các REST API đã được triển khai trong dự án, nhóm theo controller.

### Auth (`/api/auth`)

- **POST** `/register`
- **POST** `/shelter/register`
- **POST** `/verify-email`
- **POST** `/enable-2fa`
- **POST** `/request-login-otp`
- **POST** `/verify-login-otp`
- **POST** `/login`
- **GET** `/user/{userId}`
- **PUT** `/user/{userId}`

### Profile (`/api/profile`)

- **GET** `/overview`
- **GET** `/me`
- **PUT** `/me`

### Notifications (`/api/notifications`)

- **PUT** `/{id}/read`
- **PUT** `/read-all`

### Grooming Services (`/api/services/grooming`)

- **POST** `/requests`
- **GET** `/requests`
- **PUT** `/requests/{id}`

### Care Logs (`/api/care-logs`)

- **GET** `/pet/{petId}`
- **PUT** `/{id}`
- **DELETE** `/{id}`

### Pets (`/api/pets`)

- **POST** `/batch`
- **GET** `/{id}`
- **PUT** `/{id}`
- **DELETE** `/{id}`
- **GET** `/owner/{ownerId}`
- **GET** `/species/{species}`
- **GET** `/breed/{breed}`
- **POST** `/{id}/photo` (consumes `multipart/form-data`)


