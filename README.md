# Loan Application Backend System

## 📌 Overview

This project is a backend system for a digital loan application built
using **Java 17** and **Spring Boot**.\
It supports user registration, authentication, loan application
processing, installment generation, dashboard monitoring, and
notification services.

------------------------------------------------------------------------

## 🛠 Tech Stack

-   Java 17
-   Spring Boot
-   Spring Security
-   JWT Authentication
-   Spring Data JPA (Hibernate)
-   MySQL
-   Maven
-   Async Processing (Spring @Async)
-   Transaction Management (@Transactional)

------------------------------------------------------------------------

## 🏗 Architecture

Layered Architecture:

Controller → Service → Repository → Database

-   RESTful API Design
-   Stateless JWT Authentication
-   Role-Based Authorization (ADMIN / USER)
-   Transactional Business Logic
-   Async Email Notification
-   SMS Mock Notification

------------------------------------------------------------------------

## 📂 Database Design (ERD Summary)

### Entities

### 1️⃣ Users

-   id (PK)
-   full_name
-   email
-   phone
-   password
-   role (USER / ADMIN)
-   ktp_image_url
-   selfie_image_url
-   created_at
-   updated_at

### 2️⃣ Loans

-   id (PK)
-   user_id (FK)
-   amount
-   tenor_month
-   interest_rate
-   monthly_installment
-   remaining_balance
-   status (PENDING / APPROVED / REJECTED / PAID)
-   rejection_reason
-   created_at
-   updated_at

### 3️⃣ LoanPayments

-   id (PK)
-   loan_id (FK)
-   due_date
-   amount
-   status (UNPAID / PAID)

Relationships: - User (1) → (N) Loan - Loan (1) → (N) LoanPayment

------------------------------------------------------------------------

## 🔐 Security Implementation

-   BCrypt password hashing
-   JWT-based stateless authentication
-   Role-based endpoint protection
-   Business rule validation
-   Prevention of multiple active loans
-   Transactional consistency

------------------------------------------------------------------------

## 🚀 Core Features

### 👤 Authentication

-   User registration
-   Login with JWT
-   Role-based access control

### 📸 File Upload

-   Upload KTP
-   Upload Selfie
-   Stored locally with folder separation

### 💰 Loan Management

-   Apply loan (max 12,000,000)
-   Max tenor 12 months
-   Automatic installment generation
-   Prevent multiple active loans
-   Admin approve/reject with reason

### 📊 Dashboard

-   Remaining balance
-   Monthly installment
-   Total paid amount
-   Progress percentage
-   Next due date
-   Overdue status
-   Rejection reason

### 📧 Notifications

-   Async Email Notification
-   SMS Mock Notification

------------------------------------------------------------------------

## ▶️ How to Run

### 1️⃣ Create Database

``` sql
CREATE DATABASE loan_app;
```

### 2️⃣ Configure application.properties

``` properties
spring.datasource.url=jdbc:mysql://localhost:3306/loan_app
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 3️⃣ Run Application

``` bash
mvn spring-boot:run
```

Application runs at: http://localhost:8080

------------------------------------------------------------------------

## 📬 API Overview

### Authentication

-   POST /api/auth/register
-   POST /api/auth/login

### User

-   POST /api/users/upload-ktp
-   POST /api/users/upload-selfie

### Loan

-   POST /api/loans
-   GET /api/dashboard

### Admin

-   PUT /api/admin/approve/{id}
-   PUT /api/admin/reject/{id}

------------------------------------------------------------------------

## 🧠 Design Decisions

-   JWT chosen for scalability and stateless architecture
-   Transactional used to ensure data consistency
-   Async email to improve performance
-   Layered architecture for maintainability
-   DTO separation to prevent entity exposure

------------------------------------------------------------------------

## 📈 Future Improvements

-   Payment gateway integration
-   Real SMS provider integration
-   Credit scoring engine
-   Redis caching
-   Docker containerization
-   CI/CD pipeline

------------------------------------------------------------------------

## 👨‍💻 Author

Loan Application Backend System\
Built with Spring Boot & Java 17
