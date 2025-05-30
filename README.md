# 📚 Library Management System for Librarians

A Spring Boot-based backend-only Library Management System designed for librarian use. It allows managing books, members, and borrow transactions using PostgreSQL and raw SQL via `JdbcTemplate`.

---

## 🚀 Tech Stack

- **Spring Boot** (Spring MVC, Web)
- **JdbcTemplate** (raw SQL)
- **PostgreSQL**
- **Flyway** (database migration)
- **Git-Flow** (branching strategy)

---

## 📦 Features

### 1. Book Management
- Create, Read, Update, Delete (CRUD) operations
- Search books by `title`, `author`, or `isbn`
- Uses raw SQL with JdbcTemplate

### 2. Member Management
- CRUD operations for library members
- Raw SQL queries via JdbcTemplate

### 3. Borrowing Management
- Lend book to member (decrement `availableCopies`)
- Accept book returns (increment `availableCopies`)
- Join book and member data in borrow response
- Borrowing restrictions (e.g., availability, limits)

---
