-- Insert data into the category table
INSERT INTO category (id, name, description) VALUES
                                                 (nextval('category_seq'), 'Electronics', 'Devices and gadgets'),
                                                 (nextval('category_seq'), 'Books', 'Various genres of books'),
                                                 (nextval('category_seq'), 'Clothing', 'Apparel and accessories'),
                                                 (nextval('category_seq'), 'Home Appliances', 'Appliances for home use');

-- Retrieve the generated IDs for the category table
-- SELECT id, name FROM category;

-- Insert data into the product table using the correct category_id values
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES
                                                                                        (nextval('product_seq'), 'Smartphone', 'Latest model smartphone', 100, 699.99, (SELECT id FROM category WHERE name = 'Electronics')),
                                                                                        (nextval('product_seq'), 'Laptop', 'High-performance laptop', 50, 1299.99, (SELECT id FROM category WHERE name = 'Electronics')),
                                                                                        (nextval('product_seq'), 'Fiction Book', 'Bestselling fiction novel', 200, 19.99, (SELECT id FROM category WHERE name = 'Books')),
                                                                                        (nextval('product_seq'), 'T-Shirt', 'Cotton t-shirt', 150, 9.99, (SELECT id FROM category WHERE name = 'Clothing')),
                                                                                        (nextval('product_seq'), 'Microwave Oven', 'Compact microwave oven', 30, 89.99, (SELECT id FROM category WHERE name = 'Home Appliances'));