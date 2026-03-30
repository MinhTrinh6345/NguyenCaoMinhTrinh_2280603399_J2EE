-- Insert Sample Products
INSERT INTO product (name, price, description, category) VALUES
('Laptop Dell XPS 13', 1299.99, 'High-performance laptop with Intel i7 processor', 'Electronics'),
('iPhone 15', 999.99, 'Latest Apple smartphone with A17 Pro chip', 'Electronics'),
('Sony WH-1000XM5 Headphones', 399.99, 'Premium noise-cancelling wireless headphones', 'Electronics'),
('Samsung 4K Monitor', 599.99, '32-inch 4K UHD display for professional work', 'Electronics'),
('Mechanical Keyboard RGB', 149.99, 'Gaming mechanical keyboard with RGB lighting', 'Accessories'),
('Wireless Mouse', 49.99, 'Ergonomic wireless mouse for productivity', 'Accessories'),
('USB-C Hub', 79.99, '7-in-1 USB-C hub with multiple ports', 'Accessories'),
('Phone Stand', 29.99, 'Adjustable phone stand for desk or travel', 'Accessories'),
('Webcam 4K', 89.99, 'Professional 4K webcam for streaming and video calls', 'Electronics'),
('Memory Card 256GB', 59.99, 'High-speed SD card for photography and video', 'Storage');

-- Insert Sample Orders
INSERT INTO `order` (order_date, customer_name, address, phone, total_amount) VALUES
('2024-01-15 10:30:00', 'Nguyễn Cao Minh Trình', '123 Đường Lê Lợi, Quận 1, TP.HCM', '0912345678', 1499.98),
('2024-01-16 14:20:00', 'Trần Thị Mỹ Duyên', '456 Nguyễn Huệ, Quận 3, TP.HCM', '0987654321', 449.98),
('2024-01-17 09:15:00', 'Phạm Văn Dũng', '789 Lý Tự Trọng, Quận 5, TP.HCM', '0901234567', 129.98),
('2024-01-18 16:45:00', 'Vũ Thị Hương', '321 Đường Sư Vạn Hạnh, Quận 10, TP.HCM', '0923456789', 679.97);

-- Insert Sample Order Details
INSERT INTO order_detail (order_id, product_id, quantity, price) VALUES
(1, 1, 1, 1299.99),
(1, 5, 1, 149.99),
(2, 3, 1, 399.99),
(2, 6, 1, 49.99),
(3, 7, 1, 79.99),
(3, 8, 1, 49.99),
(4, 2, 1, 999.99),
(4, 9, 1, 89.99),
(4, 10, 1, 59.99);
