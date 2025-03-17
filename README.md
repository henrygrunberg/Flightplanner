# **Flightplanner Project**

This document covers everything about this project, including the overview, tech stack, time spent, and how to run it on your local machine.

Here is a link to the frontend repository if you would like to check it out:

[Frontend repository](https://github.com/henrygrunberg/Flightplanner-frontend)



## **Overview**
Flightplanner is a full-stack web application consisting of a **Spring Boot backend** with a **PostgreSQL database** and a **React frontend**.
The goal of this project is to allow users to efficiently book flights.

This document includes:
- **Tech stack**
- **Setup instructions** for the backend and frontend.
- **Challenges faced** and how they were resolved.
- **Time tracking and development notes**.
- **What I would improve**.

---

## **Technologies Used**

### **Backend**
- **Java 21**
- **Spring Boot 3.4.3**
- **Spring Web, Spring Data JPA**
- **PostgreSQL**
- **Liquibase**
- **OpenAPI (Swagger)**
- **SLF4J**
- **Lombok**
- **Gradle**

### **Frontend**
- **React 19.0.0**
- **Node.js 19+**
- **React Router**
- **Axios**
---

## **Setup Instructions**

### **1. Backend (Spring Boot + PostgreSQL)**

#### **Prerequisites**
- Install **Java 21**
- Install **Gradle**
- Install **PostgreSQL** and create an empty database

#### **Steps to Set Up Backend**

1. Clone the repository:
   ```
   git clone https://github.com/henrygrunberg/Flightplanner.git
   cd Flightplanner
   ```

2. Edit the `application.properties` file to configure **your** database connection:
   ```
   DATABASE_URL=jdbc:postgresql://localhost:5432/yourdb
   DATABASE_USERNAME=yourusername
   DATABASE_PASSWORD=yourpassword
   ```

3. Build and run the backend with Gradle:
   ```
   ./gradlew clean build
   java -jar build/libs/your-project-name.jar
   ```

4. Once the application starts, it will be available at: `http://localhost:8080`

   To see the Swagger API documentation go to: `http://localhost:8080/swagger-ui/index.html`

---

### **2. Frontend (React + Node.js 19+)**

#### **Prerequisites**
- Install **Node.js 19+**
- Install **npm** (comes with Node.js)

#### **Steps to Set Up Frontend**

1. Clone the frontend repository:
   ```
   git clone https://github.com/henrygrunberg/Flightplanner-frontend.git
   cd Flightplanner-frontend
   ```

2. Install dependencies:
   ```
   npm install
   ```

3. Run the frontend development server:
   ```
   npm run dev
   ```

   The frontend will be available at: `http://localhost:5173`

---

## **Challenges faced**

### **Backend Challenges**
#### 🛠 **Seat reccommendation for multiple passengers in different seat classes**
At the moment, if a group of passengers wants to sit together but selects different seat classes, the system does not handle it correctly.
Ideally, the passengers should be split into at least two groups based on their seat class. However, in my implementation, one of the groups gets assigned a different seat class,
and the system places all passengers together in a single class instead.

I initially attempted to solve seat placement using Breadth-First Search (BFS), as the dataset is small and BFS guarantees the shortest path.
My approach involved creating a matrix representation of the seating layout, considering up to five rows at a time:

```
[[0, 0, 0, 0, 0, 1],
[0, 1, 0, 0, 1, 0],
[0, 1, 1, 0, 0, 0],
[0, 0, 0, 0, 0, 0],
[1, 0, 0, 1, 0, 0]]

1 - available
0 - taken
```

The plan was to use BFS starting from each open seat to find the shortest valid path.
The scoring system worked as follows:

```
In the same row:
1 -> 1 == 0
1 -> 0 == 1
0 -> 1 == 1
0 -> 0 == 1

Switching rows (moving up or down)
1 -> 1 == 1
1 -> 0 == 2
0 -> 1 == 1
0 -> 0 == 2
```

My goal was to construct an optimal path with the lowest possible score and then map it to the appropriate seats.
Unfortunately, due to time constraints, I couldn’t get this approach fully working. Since I didn’t have time to debug it properly, I reverted to my previous, less optimized solution.

---

### **Frontend Challenges**
#### 🛠 **Writing CSS**

Certain CSS adjustments were unexpectedly complex.
For example, modifying one part of the layout sometimes caused issues in other sections, which required extra debugging.
---

## **Time Tracking and Development Notes**
⏳ **Estimated Time Taken:** ~ 55 hours

| Task                          | Time Spent |
|--------------------------------|------------|
| Backend Setup & Database Integration | ~19 hours |
| Frontend Setup & API Integration | ~14 hours |
| Debugging & Testing | ~10 hours |
| Writing & Generating Test Data | ~10 hours |
| Documentation | ~2 hours |

---

## **References & Help**
- **Stack Overflow** (for CSS)
- **YouTube videos** (for CSS)
- **[Uiverse.io](https://uiverse.io/)** (for CSS)
- **AI Tools (ChatGPT)** (for generating test data, helping with React and overall help)
---

## **What I would improve**
🔧 **Make Seat Recommendation with a better Algorithm** Use some real algorithm, that would guarantee 100% optimal seat assignments.

🔧 **Unit Tests & Integration Tests:** Add unit tests and integration tests to the back-end.

🔧 **Production Deployment:** Configure CI/CD pipeline for auto-deployment.

🔧 **Dockerize:** Containerize the project to eliminate manual dependency installations.

🔧 **Handle Bookings Better:** Prevent double booking of Pending seats and establish clear rules for reverting to available.

--- 
