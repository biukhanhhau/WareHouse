# 📦 Warehouse Management System (Backend API)

> A robust RESTful API built with **Spring Boot** for managing warehouse inventory, featuring Role-Based Access Control (RBAC) and JWT Authentication.

---

## 📖 Introduction

This project is a backend application designed to simulate a real-world **Warehouse Management System**. It provides secure APIs for managing products, categories, and inventory levels.

The system is built with a focus on **clean architecture**, security best practices, and data integrity. It serves as a personal project to demonstrate proficiency in enterprise-level Java development.

## 🛠 Tech Stack

* **Core:** Java 17, Spring Boot 3.x
* **Database:** PostgreSQL, Hibernate / Spring Data JPA
* **Security:** Spring Security, JWT (JSON Web Tokens), BCrypt Password Encoder
* **Validation:** Java Bean Validation (Hibernate Validator)
* **Build Tool:** Maven

## 🌟 Key Features

### 🔐 Security & Authentication
* **JWT Authentication:** Stateless authentication mechanism using JSON Web Tokens.
* **Role-Based Access Control (RBAC):**
    * `ADMIN`: Full access to create/update products and categories.
    * `USER`: Read-only access to view product lists.
* **Password Encryption:** Passwords are hashed using BCrypt before storing in the database.

### 📦 Inventory Management
* **Product Management:** CRUD operations for products (Name, Price, SKU, Quantity).
* **Category Management:** Organize products into categories (Electronics, Household, etc.).
* **Database Relationships:** Implemented **One-to-Many** relationship between Categories and Products using JPA.

### 🛡️ Robust Architecture
* **DTO Pattern:** Used Data Transfer Objects to decouple the internal database entities from the external API representation.
* **Global Exception Handling:** Centralized error handling using `@RestControllerAdvice` to provide clean, user-friendly JSON error responses.
* **Data Validation:** Strong validation on input fields (e.g., Price must be positive, Name cannot be blank) to ensure data integrity.

---

## 🗂 Database Design (ERD)

<img width="542" height="748" alt="image (1)" src="https://github.com/user-attachments/assets/b53729ab-3e5d-40b8-ac83-a6020b55e261" />


`Categories (1) <---> (N) Products`

---

## 🚀 API Endpoints
<img width="1920" height="1550" alt="image" src="https://github.com/user-attachments/assets/f36a0776-b4bb-4f18-a7bb-7c11cfc71cca" />

### 1. Authentication
| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `POST` | `/auth/register` | Register a new user account | Public |
| `POST` | `/auth/login` | Login to receive a Bearer Token | Public |

### 2. Categories
| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `GET` | `/categories` | Get list of all categories | Public/User |
| `POST` | `/categories` | Create a new category | **Admin Only** |

### 3. Products
| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `GET` | `/products` | Get list of all products | Public/User |
| `POST` | `/products` | Add a new product (with Category ID) | **Admin Only** |
| `PUT` | `/products/{id}` | Update product details & Category | **Admin Only** |

---

## ⚙️ How to Run

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/biukhanhhau/WareHouse.git
    ```

2.  **Configure Database:**
    * Create a PostgreSQL database named `warehouse_db` (or update `application.properties`).
    * Update your username/password in `src/main/resources/application.properties`.

3.  **Run the Application:**
    ```bash
    mvn spring-boot:run
    ```

4.  **Test APIs:**
    * Import the provided `Postman_Collection.json` (located in the root folder) into Postman.
    * Or access Swagger UI (if configured).

---

## 🧪 Example Response (Error Handling)

Instead of a generic 500 or 400 error, the system returns specific validation messages:

```json
{
    "price": "Price must be greater than 0",
    "name": "Product name cannot be blank"
}


