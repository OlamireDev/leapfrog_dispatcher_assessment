# 📦 Leap Frog Box Delivery Management Service

A Spring Boot REST API for managing autonomous delivery boxes capable of transporting small items to remote locations. This service provides endpoints for registering boxes, loading them with items, checking their status, and monitoring battery levels.

## Objective
This system simulates communication with smart delivery boxes. Actual IoT/device communication is beyond the scope of this project — only backend logic and REST interfaces are implemented.

## ✅ Features
### **Boxes**

- Register a new box
- Check available boxes for loading
- Load items into a box
- Check items loaded in a box
- Check box battery level

### **Items**
- Get all items


## 📐 Assumptions & Design Decisions
1. Boxes ->	Each box has a unique txref and acts as an id (≤ 20 characters)
2. Battery Level ->	Battery capacity has been renamed to battery level is stored as percentage (0–100)
3. Box Availability	Only boxes in IDLE or LOADING state and battery ≥ 25% are loadable
4. Persistence	H2 in-memory database for simplicity
5. Items are preloaded via JSON, assuming items have to be predefined before adding to box
6. Default boxes added to DB on startup for convenience 
## 🧠 Technologies

- Java 21

- Spring Boot 3.5.8

- Spring Web

- Spring Data JPA

- Spring Validation
- 
- Spring Doc

- H2 Database

- Maven

- Lombok

## 🚀 Running the Application
 ### Using Maven

 ```mvn spring-boot:run```

### Using JAR
```bash 
    mvn clean package 
    java -jar target/leapfrog_dispatcher-0.0.1-SNAPSHOT.jar
```

## 🛠 API Endpoints
### 📦 Boxes
Method	    Endpoint	    Description
- POST	    /api/v1/box 	Create a box
- PUT	    /api/v1/box/{box_txref}/load	Load/Unload a box with items
- GET	    /api/v1/box/{box_txref}/loaded_items	View loaded items
- GET	    /api/v1/box/available	View available boxes
- GET	    /api/v1/box/{box_txref}/battery_level	View box current battery level
### 🏷 Items
Method	Endpoint	Description
- GET	/api/v1/item/all	View all items

### Documentation
Swagger documentation can be found here(when the application is running): http://localhost:8891/swagger-ui/index.html

## 📊 Database Console

H2 console available at(when the application is running):

http://localhost:8891/h2-console

JDBC_URL: jdbc:h2:mem:leapfrog_dispatcher 

## 📦 To-Do / Enhancements

- Authentication & Roles

- Battery drain simulation during delivery

- Audit logging (delivery logs)
- MORE TESTS

## 📝 License

This project is solely for evaluation and educational purposes.