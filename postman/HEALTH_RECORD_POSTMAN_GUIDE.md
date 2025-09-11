# Hướng dẫn test Health Record API với Postman

## Cách import collection

1. Mở Postman
2. Click "Import"
3. Chọn file `HealthRecord.postman_collection.json`
4. Collection sẽ được import với tên "Pet Health Record API"

## Thứ tự test các API

### 1. Tạo dữ liệu mẫu (Chạy theo thứ tự)

1. **Create Health Record - Vaccination**

   - Tạo hồ sơ tiêm chủng đầu tiên
   - Lưu lại `id` từ response để test các API khác

2. **Create Health Record - Allergy**

   - Tạo hồ sơ dị ứng

3. **Create Health Record - Disease**

   - Tạo hồ sơ bệnh tật

4. **Create Health Record - Treatment**

   - Tạo hồ sơ điều trị

5. **Create Health Record - CHECKUP**

   - Tạo hồ sơ khám sức khỏe

6. **Create Health Record - MEDICATION**
   - Tạo hồ sơ thuốc men

### 2. Test các API đọc dữ liệu

7. **Get Health Record by ID**

   - Thay `{id}` bằng ID từ response của bước 1
   - Kiểm tra chi tiết hồ sơ

8. **Get Health Records by Pet ID**

   - Lấy tất cả hồ sơ của pet ID = 1
   - Kiểm tra danh sách đầy đủ

9. **Get Timeline View**
   - Xem dòng thời gian hồ sơ sức khỏe
   - Kiểm tra format timeline với icon và nhóm theo ngày

### 3. Test các API filter

10. **Filter by Type - VACCINATION**

    - Lọc chỉ hồ sơ tiêm chủng
    - Kiểm tra kết quả chỉ có VACCINATION

11. **Filter by Type - ALLERGY**

    - Lọc chỉ hồ sơ dị ứng

12. **Filter by Type - DISEASE**

    - Lọc chỉ hồ sơ bệnh tật

13. **Filter by Type - TREATMENT**

    - Lọc chỉ hồ sơ điều trị

14. **Get Available Record Types**
    - Lấy danh sách các loại hồ sơ có sẵn
    - Kiểm tra response có đủ các loại

### 4. Test API cập nhật

15. **Update Health Record**
    - Cập nhật hồ sơ đã tạo ở bước 1
    - Thay `{id}` bằng ID từ response của bước 1
    - Kiểm tra dữ liệu đã được cập nhật

### 5. Test API xóa (Tùy chọn)

16. **Delete Health Record**
    - Xóa hồ sơ (chỉ test nếu muốn)
    - Thay `{id}` bằng ID muốn xóa

## Các trường dữ liệu quan trọng

### Thông tin cơ bản

- `petId`: ID của thú cưng (sử dụng 1 cho test)
- `vetId`: ID của bác sĩ thú y (sử dụng 2 cho test)
- `visitDate`: Ngày khám (format ISO 8601)
- `recordType`: Loại hồ sơ (VACCINATION, ALLERGY, DISEASE, TREATMENT, MEDICATION, CHECKUP)

### Thông tin tiêm chủng (khi recordType = VACCINATION)

- `vaccineType`: Loại vắc-xin
- `vaccineBatch`: Số lô vắc-xin
- `nextVaccinationDate`: Ngày tiêm tiếp theo

### Thông tin dị ứng (khi recordType = ALLERGY)

- `allergyType`: Loại dị ứng (THUOC, THUC_AN, KHAC)
- `allergyDescription`: Mô tả chi tiết

### Thông tin thuốc men (khi recordType = MEDICATION hoặc TREATMENT)

- `medicationName`: Tên thuốc
- `medicationDosage`: Liều lượng
- `medicationFrequency`: Tần suất uống
- `medicationDuration`: Thời gian điều trị

### Thông tin bác sĩ

- `doctorName`: Tên bác sĩ
- `clinicName`: Tên phòng khám

## Kiểm tra Response

### Response thành công

```json
{
  "success": true,
  "message": "Created/OK/Updated",
  "data": { ... }
}
```

### Timeline Response

```json
{
  "success": true,
  "message": "OK",
  "data": [
    {
      "date": "2024-01-15",
      "items": [
        {
          "id": 1,
          "time": "2024-01-15T10:30:00",
          "recordType": "VACCINATION",
          "title": "Tiêm chủng: Rabisin",
          "description": "...",
          "doctorName": "Dr. Nguyễn Văn A",
          "clinicName": "Athena Veterinary Clinic",
          "icon": "💉"
        }
      ]
    }
  ]
}
```

## Lưu ý

1. **Thay đổi ID**: Trong các request cần ID, hãy thay `{id}` bằng ID thực tế từ response
2. **Pet ID**: Đảm bảo pet ID = 1 tồn tại trong database
3. **Vet ID**: Đảm bảo vet ID = 2 tồn tại trong database
4. **Thứ tự test**: Nên test theo thứ tự để có dữ liệu đầy đủ cho timeline view
5. **Error handling**: Kiểm tra response khi có lỗi (400, 404, 500)

## Troubleshooting

- **404 Not Found**: Kiểm tra pet ID và vet ID có tồn tại không
- **400 Bad Request**: Kiểm tra format JSON và các trường bắt buộc
- **500 Internal Server Error**: Kiểm tra server có chạy không và database connection
