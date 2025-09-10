## Pet Care - Danh sách API (theo bảng)

Bên dưới là danh sách các REST API đã triển khai, trình bày theo định dạng: Tên | URL | Mô tả.

### Auth (`/api/auth`)

| Tên               | URL                                              | Mô tả                         |
| ----------------- | ------------------------------------------------ | ----------------------------- |
| Register          | http://localhost:8080/api/auth/register          | Đăng ký tài khoản người dùng  |
| Shelter Register  | http://localhost:8080/api/auth/shelter/register  | Đăng ký tài khoản shelter     |
| Verify Email      | http://localhost:8080/api/auth/verify-email      | Xác thực email                |
| Enable 2FA        | http://localhost:8080/api/auth/enable-2fa        | Bật xác thực 2 lớp            |
| Request Login OTP | http://localhost:8080/api/auth/request-login-otp | Yêu cầu OTP đăng nhập         |
| Verify Login OTP  | http://localhost:8080/api/auth/verify-login-otp  | Xác thực OTP đăng nhập        |
| Login             | http://localhost:8080/api/auth/login             | Đăng nhập                     |
| Get User          | http://localhost:8080/api/auth/user/{userId}     | Lấy thông tin người dùng      |
| Update User       | http://localhost:8080/api/auth/user/{userId}     | Cập nhật thông tin người dùng |

### Profile (`/api/profile`)

| Tên         | URL                                        | Mô tả                       |
| ----------- | ------------------------------------------ | --------------------------- |
| Overview    | http://localhost:8080/api/profile/overview | Thống kê/overview hồ sơ     |
| Me (Get)    | http://localhost:8080/api/profile/me       | Lấy thông tin bản thân      |
| Me (Update) | http://localhost:8080/api/profile/me       | Cập nhật thông tin bản thân |

### Notifications (`/api/notifications`)

| Tên           | URL                                               | Mô tả                     |
| ------------- | ------------------------------------------------- | ------------------------- |
| Mark Read     | http://localhost:8080/api/notifications/{id}/read | Đánh dấu thông báo đã đọc |
| Mark All Read | http://localhost:8080/api/notifications/read-all  | Đánh dấu tất cả đã đọc    |

### Grooming Services (`/api/services/grooming`)

| Tên            | URL                                                                    | Mô tả                        |
| -------------- | ---------------------------------------------------------------------- | ---------------------------- |
| Create Request | http://localhost:8080/api/services/grooming/requests                   | Tạo yêu cầu grooming         |
| List Requests  | http://localhost:8080/api/services/grooming/requests?ownerId={ownerId} | Danh sách yêu cầu theo owner |
| Update Request | http://localhost:8080/api/services/grooming/requests/{id}              | Cập nhật yêu cầu             |

### Care Logs (`/api/care-logs`)

| Tên         | URL                                             | Mô tả                       |
| ----------- | ----------------------------------------------- | --------------------------- |
| List by Pet | http://localhost:8080/api/care-logs/pet/{petId} | Danh sách care-log theo pet |
| Update      | http://localhost:8080/api/care-logs/{id}        | Cập nhật care-log           |
| Delete      | http://localhost:8080/api/care-logs/{id}        | Xóa care-log                |

### Pets (`/api/pets`)

| Tên                  | URL                                                     | Mô tả                                  |
| -------------------- | ------------------------------------------------------- | -------------------------------------- |
| Create               | http://localhost:8080/api/pets                          | Tạo thú cưng                           |
| Create Batch         | http://localhost:8080/api/pets/batch                    | Tạo nhiều thú cưng                     |
| Get by Id            | http://localhost:8080/api/pets/{id}                     | Xem chi tiết thú cưng                  |
| Update               | http://localhost:8080/api/pets/{id}                     | Chỉnh sửa thú cưng                     |
| Delete               | http://localhost:8080/api/pets/{id}                     | Xóa thú cưng                           |
| List by Owner        | http://localhost:8080/api/pets/owner/{ownerId}          | Danh sách thú cưng theo owner          |
| List by Species      | http://localhost:8080/api/pets/species/{species}        | Danh sách theo loài                    |
| List by Breed        | http://localhost:8080/api/pets/breed/{breed}            | Danh sách theo giống                   |
| Upload Main Photo    | http://localhost:8080/api/pets/{id}/photo               | Tải ảnh đại diện (multipart/form-data) |
| List Gallery Photos  | http://localhost:8080/api/pets/{id}/photos              | Danh sách ảnh thư viện                 |
| Delete Gallery Photo | http://localhost:8080/api/pets/{petId}/photos/{photoId} | Xóa ảnh thư viện                       |

### Health Records (`/api/health-records`)

| Tên         | URL                                                  | Mô tả                         |
| ----------- | ---------------------------------------------------- | ----------------------------- |
| Create      | http://localhost:8080/api/health-records             | Tạo hồ sơ bệnh                |
| Get by Id   | http://localhost:8080/api/health-records/{id}        | Xem chi tiết hồ sơ bệnh       |
| List by Pet | http://localhost:8080/api/health-records/pet/{petId} | Danh sách hồ sơ bệnh theo pet |
| Update      | http://localhost:8080/api/health-records/{id}        | Cập nhật hồ sơ bệnh           |
| Delete      | http://localhost:8080/api/health-records/{id}        | Xóa hồ sơ bệnh                |

### Family Sharing (`/api/family`)

| Tên                | URL                                                                        | Mô tả                         |
| ------------------ | -------------------------------------------------------------------------- | ----------------------------- |
| Invite Member      | http://localhost:8080/api/family/invitations                               | Mời thành viên gia đình       |
| Accept Invitation  | http://localhost:8080/api/family/invitations/accept?currentUserId={userId} | Chấp nhận lời mời bằng token  |
| List Members       | http://localhost:8080/api/family/members?ownerId={ownerId}                 | Danh sách thành viên và quyền |
| Update Member Role | http://localhost:8080/api/family/members/{id}/role                         | Cập nhật quyền thành viên     |
| Revoke Member      | http://localhost:8080/api/family/members/{id}                              | Thu hồi quyền truy cập        |
