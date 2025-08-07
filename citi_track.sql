-- Create database
CREATE DATABASE IF NOT EXISTS citi_track;
USE citi_track;

-- Drop and recreate users table
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Drop and recreate admins table
DROP TABLE IF EXISTS admins;
CREATE TABLE admins (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Insert default admin
INSERT INTO admins (username, password) VALUES ('admin', 'admin123');

-- Drop and recreate departments table
DROP TABLE IF EXISTS departments;
CREATE TABLE departments (
    dept_id INT AUTO_INCREMENT PRIMARY KEY,
    dept_name VARCHAR(100) NOT NULL
);

-- Insert default departments
INSERT INTO departments (dept_name) VALUES 
('Roads'),
('Street Lights'),
('Sanitation'),
('Drainage'),
('Garbage Collection'),
('Street Signs'),
('Potholes');

-- Drop and recreate complaints table
DROP TABLE IF EXISTS complaints;
CREATE TABLE complaints (
    complaint_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    dept_id INT NOT NULL,
    description TEXT NOT NULL,
    status ENUM('Pending', 'In Progress', 'Resolved') DEFAULT 'Pending',
    date_reported TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id) ON DELETE CASCADE
);

-- Final Admin View Query to see all complaints
SELECT 
    c.complaint_id,
    u.name AS user_name,
    d.dept_name AS department_name,
    c.description,
    c.status,
    c.date_reported
FROM complaints c
JOIN users u ON c.user_id = u.user_id
JOIN departments d ON c.dept_id = d.dept_id
ORDER BY c.date_reported DESC;
