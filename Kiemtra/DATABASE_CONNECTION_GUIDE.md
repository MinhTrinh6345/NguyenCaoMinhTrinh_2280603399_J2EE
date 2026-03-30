# Database Connection & Data Persistence Guide

## Summary of Changes

Your Kiemtra project has been fully updated to connect to the MySQL database and persist all data. Here's what was done:

### 1. **Model Classes Updated with JPA Annotations**

All model classes now have proper JPA/Hibernate annotations for database mapping:

#### Product.java
- `@Entity @Table(name = "product")`
- `@Id @GeneratedValue(strategy = GenerationType.IDENTITY)` on id field
- `@Column` annotations for database columns
- Auto-increments ID on insert

#### Order.java
- `@Entity @Table(name = "order")`
- One-to-Many relationship with OrderDetail: `@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)`
- All fields mapped to database columns
- `orderDate` automatically set to `LocalDateTime.now()`

#### OrderDetail.java
- `@Entity @Table(name = "order_detail")`
- Many-to-One relationship with Order: `@ManyToOne @JoinColumn(name = "order_id")`
- Many-to-One relationship with Product: `@ManyToOne @JoinColumn(name = "product_id")`
- Stores product price at time of order

### 2. **Repository Interfaces Created**

Three Spring Data JPA repositories for database operations:

- `ProductRepository` - for Product CRUD and queries
- `OrderRepository` - for Order CRUD and queries
- `OrderDetailRepository` - for OrderDetail CRUD and queries

### 3. **Service Classes Updated to Use Repositories**

#### ProductService
- Now uses `ProductRepository` instead of in-memory list
- `getAllProducts()` - fetches all products from database
- `searchProducts()` - searches via database query
- `getProductById()` - gets single product by ID
- `saveProduct()` - saves product to database

#### OrderService  
- Now uses `OrderRepository` and `OrderDetailRepository`
- `saveOrder()` - saves complete order with all details to database
- `getAllOrders()` - fetches all orders from database
- `getOrderById()` - gets single order by ID
- Additional query methods for customer name and date range searches

### 4. **Controllers Updated for Database Integration**

#### CartController
- Updated `addToCart()` to use `ProductService.getProductById()` for better performance
- Updated `processCheckout()` to create proper OrderDetail objects with order reference
- **Key improvement**: Orders now automatically save to database when checkout is completed
- Cart is cleared after successful order placement

#### AdminController & ProductController
- No changes needed - they automatically use updated services

### 5. **Automatic Data Initialization**

New `DataInitializer.java` configuration class:
- Automatically loads 10 sample products on application startup
- Only runs if database is empty (safe for production)
- Logs success message to console

### 6. **Database Configuration**

File: `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/course_db
spring.datasource.username=root
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 7. **Database Schema**

Three fully related tables with proper constraints:

```
product (id, name, price, description, category)
    ↓ (referenced by)
order_detail (id, order_id*, product_id*, quantity, price)
    ↑ (references)
`order` (id, order_date, customer_name, address, phone, total_amount)
```

- Foreign keys with CASCADE delete for order → order_detail
- Indexes on frequently queried columns
- UTF-8 support for Vietnamese language

## Quick Start

### Step 1: Create Database
```sql
CREATE DATABASE course_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Step 2: Run the Application
```bash
.\mvnw.cmd spring-boot:run
```

Or compile and run JAR:
```bash
.\mvnw.cmd clean package
# Then run the JAR file from target directory
```

### Step 3: Access the Application
- **Home Page**: http://localhost:8080/
- **Product Search**: http://localhost:8080/products/search
- **Cart**: http://localhost:8080/cart
- **Admin Login**: http://localhost:8080/admin/login
  - Username: `admin`
  - Password: `123456`

## Data Persistence Flow

### Adding Products to Cart
1. User clicks "Add to Cart"
2. Product loaded from database via `ProductService.getProductById()`
3. Product stored in session cart (not in database)
4. User can modify quantities in session

### Checkout Process
1. User fills in customer information (name, phone, address)
2. System creates Order object with:
   - Customer details
   - Order date (set to current time)
   - Total amount calculated from cart items
3. For each cart item:
   - Creates OrderDetail object
   - Links to Order and Product
   - Records product price at time of order
4. **Entire Order + OrderDetails saved to database** via `OrderService.saveOrder()`
5. Session cart cleared after successful save
6. User redirected to success page

### Admin Dashboard
- Displays all orders saved in database
- Each order shows complete details with items purchased

## Data Persistence Verification

You can verify data is being saved:

### Via Admin Dashboard
1. Go to http://localhost:8080/admin/login
2. Login with admin/123456
3. Click "Admin Dashboard"
4. All orders appear in the table

### Via MySQL
```sql
USE course_db;

-- View all products
SELECT * FROM product;

-- View all orders
SELECT * FROM `order`;

-- View order details with product names
SELECT o.id, o.customer_name, p.name, od.quantity, od.price
FROM `order` o
JOIN order_detail od ON o.id = od.order_id
JOIN product p ON od.product_id = p.id
ORDER BY o.order_date DESC;
```

## Complete Data Flow Diagram

```
User Actions
    ↓
ProductController/CartController (UI layer)
    ↓
ProductService/OrderService (Business logic)
    ↓
ProductRepository/OrderRepository (Data access layer)
    ↓
Spring Data JPA (Hibernate)
    ↓
MySQL Database (Persistence)
```

## Important Notes

1. **Session vs Database**:
   - Cart items are stored in session (temporary)
   - Orders and products are stored in database (permanent)

2. **Order Relationships**:
   - One Order has many OrderDetails
   - One OrderDetail references one Order and one Product
   - Deleting an order automatically deletes its order details (CASCADE)

3. **Automatic DDL**:
   - `spring.jpa.hibernate.ddl-auto=update` allows Hibernate to auto-create/update tables
   - For production, change to `validate` to prevent unwanted modifications

4. **Sample Data**:
   - First run loads 10 sample products automatically
   - Subsequent runs detect existing data and skip initialization
   - No duplicate data will be inserted

## Files Created/Modified

**Created:**
- `src/main/resources/db/schema.sql` - Database schema
- `src/main/resources/db/data.sql` - Sample data
- `src/main/java/com/example/Kiemtra/repository/ProductRepository.java`
- `src/main/java/com/example/Kiemtra/repository/OrderRepository.java`
- `src/main/java/com/example/Kiemtra/repository/OrderDetailRepository.java`
- `src/main/java/com/example/Kiemtra/config/DataInitializer.java`

**Updated:**
- `src/main/java/com/example/Kiemtra/model/Product.java` (added JPA annotations)
- `src/main/java/com/example/Kiemtra/model/Order.java` (added JPA annotations)
- `src/main/java/com/example/Kiemtra/model/OrderDetail.java` (added JPA annotations)
- `src/main/java/com/example/Kiemtra/service/ProductService.java` (uses repository)
- `src/main/java/com/example/Kiemtra/service/OrderService.java` (uses repository)
- `src/main/java/com/example/Kiemtra/controller/CartController.java` (saves to database)

## Troubleshooting

### Database connection fails
- Verify MySQL is running on localhost:3306
- Check username/password in application.properties
- Ensure course_db database exists

### No products appear
- Check that DataInitializer ran (look for ✅ message in console)
- Manually insert sample data from `db/data.sql`

### Orders not saving
- Check for errors in console output
- Verify all @Entity classes have @Id fields
- Ensure repositories are being injected properly

### Tables don't exist
- Ensure `spring.jpa.hibernate.ddl-auto=update` is set
- Restart the application to trigger Hibernate DDL
- Check MySQL for errors using `SHOW TABLES;`

## Success!

Your Kiemtra project now has:
✅ Full database connectivity
✅ Persistent data storage
✅ Automatic data initialization  
✅ Complete order management system
✅ Data retrieval and display
✅ Admin dashboard showing saved orders
