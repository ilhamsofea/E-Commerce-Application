# 🛒 Electronic Shop Application

## Project Overview
The purpose of this project is to develop an **electronic shop application** where users can:
- View product information  
- Add products to cart  
- Make purchases  

This system simulates a basic e-commerce platform with user authentication, product browsing, and checkout functionalities.

---

## System Flow
The application follows this general flow:
1. User login / registration  
2. Access main menu  
3. Browse products  
4. View product details  
5. Add to cart  
6. Checkout & payment  
7. Logout  

---

## System Screens & Features

### 1. Login Page
- Users enter username and password  
- Option to show/hide password  

### 2. Registration Page
- New users can sign up with a unique username  
- Validation:
  - Prevent duplicate usernames  
  - Error messages for empty/invalid fields  
- User data is stored in a text file  

### 3. Main Page
- Displays:
  - User icon  
  - Logout option  
  - Product browsing  
  - Purchase functionality  

### 4. User Profile
- Displays user's name and username  

### 5. Product Listing
- Shows available products (e.g., mobile, tablet, laptop)  
- Organized in multiple frames  

### 6. Product Details
- Detailed product information:
  - Features  
  - Price  
  - Description  

### 7. Cart System
- Add products to cart  
- View selected items  
- Remove items  
- Automatically updates total price  

### 8. Checkout Process
- Enter shipping details  
- Enter card information  
- Payment processing with progress bar  

### 9. Payment Verification
- Random code generation  
- User enters code to confirm payment  

### 10. Order Completion
- Displays shipping number  
- Cart is cleared after successful payment  

### 11. Logout
- Confirmation prompt before logging out  
- Redirects back to login page  

---

## Main Functionalities

### Cart Management
- Implemented using `JTable`  
- Handles:
  - Product listing  
  - Price calculation  
  - Cart updates  

### Input Validation
- Uses **exception handling** to:
  - Validate user inputs  
  - Prevent invalid operations  

---

## Object-Oriented Concepts Used

### Polymorphism
- Implemented using the **Timer class**
- Method overriding used to define custom timer behavior  
- Handles delayed execution (e.g., payment verification code)

---

### Inheritance
- `Product` class acts as a **base class**
- Contains shared attributes:
  - Brand  
  - Model  
  - Price  
  - Description  
- `ProductModel` class extends functionality to categorize products:
  - Mobile  
  - Tablet  
  - Laptop  

---

### Multithreading
- Timer runs on a separate thread  
- Ensures:
  - UI remains responsive  
  - Smooth user experience during delays (e.g., 3-second processing)

---

### File I/O

#### 1. Saving User Data
- Stored in `users.txt`  
- Uses:
  - `FileWriter`  
  - `PrintWriter`  
- Data is appended (no overwrite)

#### 2. Username Validation
- Reads file using `BufferedReader`  
- Checks for existing usernames  
- Prevents duplicate registration  

---

## Conclusion
This project demonstrates a basic **e-commerce system** with:
- User authentication  
- Product management  
- Cart and checkout system  
- Payment simulation  

It also applies key **Object-Oriented Programming (OOP)** concepts and **file handling techniques** in Java.

---  
