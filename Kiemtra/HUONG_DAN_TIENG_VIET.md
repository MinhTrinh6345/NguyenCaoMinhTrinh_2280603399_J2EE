# Hướng Dẫn Kết Nối & Lưu Trữ Dữ Liệu - Tiếng Việt

## Tóm Tắt Các Thay Đổi

Dự án Kiemtra của bạn đã được cập nhật hoàn toàn để kết nối với cơ sở dữ liệu MySQL và lưu trữ tất cả dữ liệu.

### ✅ Những gì đã được thực hiện:

#### 1. **Cập nhật Model Classes với JPA Annotations**

- **Product.java**: Ánh xạ đến bảng `product` 
- **Order.java**: Ánh xạ đến bảng `order` với quan hệ One-to-Many tới OrderDetail
- **OrderDetail.java**: Ánh xạ đến bảng `order_detail` với khóa ngoại tới Order và Product

#### 2. **Tạo Repository Interfaces**

3 Repository Spring Data JPA:
- `ProductRepository` - Quản lý sản phẩm
- `OrderRepository` - Quản lý đơn hàng
- `OrderDetailRepository` - Quản lý chi tiết đơn hàng

#### 3. **Cập nhật Service Classes**

- **ProductService**: Sử dụng ProductRepository thay vì danh sách trong bộ nhớ
- **OrderService**: Sử dụng OrderRepository + OrderDetailRepository để lưu đơn hàng vào database

#### 4. **Cập nhật Controllers**

- **CartController**: Khi người dùng hoàn tất thanh toán (checkout), đơn hàng được lưu vào database tự động

#### 5. **Tự động Tải Dữ Liệu Mẫu**

- **DataInitializer.java**: Tự động tải 10 sản phẩm mẫu khi ứng dụng khởi động
- Chỉ chạy 1 lần nếu cơ sở dữ liệu trống

## 🚀 Bắt Đầu Nhanh

### Bước 1: Tạo Database
```sql
CREATE DATABASE course_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Bước 2: Chạy Ứng Dụng
```bash
cd d:\Trinh\PT_J2EE\NguyenCaoMinhTrinh_2280603399_J2EE\Kiemtra
.\mvnw.cmd spring-boot:run
```

### Bước 3: Truy Cập Ứng Dụng
- **Trang Chủ**: http://localhost:8080/
- **Tìm Kiếm Sản Phẩm**: http://localhost:8080/products/search
- **Giỏ Hàng**: http://localhost:8080/cart
- **Quản Trị Viên**: http://localhost:8080/admin/login
  - Tên đăng nhập: `admin`
  - Mật khẩu: `123456`

## 💾 Quá Trình Lưu Trữ Dữ Liệu

### Quy Trình Thanh Toán (Checkout):

1. **Người dùng thêm sản phẩm vào giỏ hàng**
   - Sản phẩm được lấy từ database
   - Được lưu trong session (không lưu vào database lúc này)

2. **Người dùng điền thông tin khách hàng**
   - Tên
   - Số điện thoại
   - Địa chỉ giao hàng

3. **Nhấn "Hoàn Thành Đơn Hàng"**
   - Hệ thống tạo đối tượng Order
   - Tính tổng tiền từ các sản phẩm trong giỏ
   - Tạo OrderDetail cho mỗi sản phẩm

4. **💾 LƯU VÀO DATABASE**
   - Toàn bộ Order + OrderDetails được lưu vào database
   - Giỏ hàng trong session được xóa
   - Hiển thị trang thành công (Checkout Success)

### Xem Đơn Hàng Đã Lưu:

1. Vào http://localhost:8080/admin/login
2. Đăng nhập: admin / 123456
3. Xem "Admin Dashboard"
4. Tất cả đơn hàng đã lưu được hiển thị trong bảng

## 🔍 Kiểm Tra Dữ Liệu Trong MySQL

```sql
USE course_db;

-- Xem tất cả sản phẩm
SELECT * FROM product;

-- Xem tất cả đơn hàng
SELECT * FROM `order`;

-- Xem chi tiết đơn hàng với tên sản phẩm
SELECT o.id, o.customer_name, p.name, od.quantity, od.price
FROM `order` o
JOIN order_detail od ON o.id = od.order_id
JOIN product p ON od.product_id = p.id
ORDER BY o.order_date DESC;
```

## 📊 Cấu Trúc Database

```
BẢNG: product
├── id (AUTO_INCREMENT)
├── name
├── price
├── description
└── category

BẢNG: order
├── id (AUTO_INCREMENT)
├── order_date
├── customer_name
├── address
├── phone
└── total_amount

BẢNG: order_detail (Bảng trung gian)
├── id (AUTO_INCREMENT)
├── order_id (FK -> order)
├── product_id (FK -> product)
├── quantity
└── price
```

## 📝 Cấu Hình Ứng Dụng

File: `src/main/resources/application.properties`

```properties
spring.application.name=Kiemtra
spring.datasource.url=jdbc:mysql://localhost:3306/course_db
spring.datasource.username=root
spring.datasource.password=123456

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## ⚠️ Lưu Ý Quan Trọng

1. **Giỏ Hàng vs Database**:
   - Giỏ hàng được lưu trong session (tạm thời)
   - Đơn hàng được lưu vào database MySQL (vĩnh viễn)

2. **Dữ Liệu Mẫu**:
   - Lần chạy đầu tiên: Tự động tải 10 sản phẩm
   - Lần chạy tiếp theo: Bỏ qua nếu dữ liệu đã tồn tại

3. **Xóa Đơn Hàng**:
   - Xóa order tự động xóa order_detail liên quan (CASCADE DELETE)

## 🔧 Khắc Phục Sự Cố

| Vấn Đề | Giải Pháp |
|--------|----------|
| Kết nối database thất bại | Kiểm tra MySQL đang chạy, kiểm tra username/password |
| Không có sản phẩm hiển thị | Kiểm tra console có ✅ DataInitializer message |
| Đơn hàng không được lưu | Kiểm tra console có lỗi nào, kiểm tra server log |
| Bảng không tồn tại | Khởi động lại ứng dụng để Hibernate tạo bảng |

## ✨ Hoàn Thành

Dự án Kiemtra của bạn hiện đã có:
- ✅ Kết nối Database MySQL
- ✅ Lưu trữ dữ liệu vĩnh viễn
- ✅ Tự động tải sản phẩm mẫu
- ✅ Lưu đơn hàng khi thanh toán
- ✅ Hiển thị đơn hàng trên Admin Dashboard
- ✅ Hỗ trợ tiếng Việt UTF-8

Happy Coding! 🎉
