# 🧠 Smart Resume Screener

> **An AI-powered Spring Boot application that parses, analyzes, and scores resumes using NLP and machine learning techniques.**

![Java](https://img.shields.io/badge/Backend-Java%20SpringBoot-orange)
![Database](https://img.shields.io/badge/Database-MySQL-blue)
![Frontend](https://img.shields.io/badge/Frontend-HTML%2C%20CSS%2C%20JS-green)
![ML](https://img.shields.io/badge/ML-NLP%20%26%20Keyword%20Analysis-yellow)
![Status](https://img.shields.io/badge/Status-Active-success)

---

## 🧠 Overview

**Smart Resume Screener** is an intelligent system designed to automate the process of resume evaluation.  
It extracts candidate details, analyzes resume content, and generates a **job-fit score** based on relevant skills and experience.

This system mimics how professional hiring platforms like **LinkedIn Talent Insights** or **Naukri.com** screen resumes automatically.

### 🔍 Key Features:
- 📤 Upload resumes (PDF/TXT format)
- 📄 Extract information like name, email, phone, and skills
- 📊 Analyze and score resumes based on job roles
- 💡 Recommend missing or extra skills for better fit
- ⚙️ REST API integration with a simple HTML-CSS-JS frontend

---

## ⚙️ Tech Stack

### 🔹 Backend
- **Java 17**, **Spring Boot**, **Spring Data JPA**
- **Hibernate ORM**
- **Apache Tika** for text extraction
- **Regex + NLP-based parsing**

### 🔹 Frontend
- **HTML5**, **CSS3**, **JavaScript (Vanilla JS)**
- Responsive dashboard-style UI inspired by Bolt
- Fetch API for backend communication

### 🔹 Database
- **MySQL** (for resume storage and scoring data)
- **MongoDB** (optional for unstructured data)

---

## 📂 Project Structure

smart_resume_screener/
│
├── backend/
│ ├── src/main/java/com/example/smart_resume_screener/
│ │ ├── controller/
│ │ │ └── ResumeController.java
│ │ ├── model/
│ │ │ ├── ResumeDocument.java
│ │ │ └── ParsedResume.java
│ │ ├── services/
│ │ │ ├── ResumeService.java
│ │ │ └── ResumeParsingService.java
│ │ ├── repository/
│ └── SmartResumeScreenerApplication.java
│
├── frontend/
│ ├── index.html
│ ├── style.css
│ └── script.js
│
└── README.md

yaml
Copy code

---

## 🧩 API Endpoints

| Endpoint | Method | Description |
|-----------|---------|-------------|
| `/api/resume/upload` | `POST` | Upload and save a resume file |
| `/api/resume/analyze/{id}` | `GET` | Extract and analyze resume content |
| `/api/resume/score/{id}?role=Java Developer` | `GET` | Score the resume against a specific job role |

---

## 🚀 How to Run the Project

### 🔸 Backend Setup (Spring Boot)
```bash
# Clone the repository
git clone https://github.com/<your-username>/smart-resume-screener.git
cd smart-resume-screener/backend

# Run the Spring Boot application
mvn spring-boot:run
Backend will run on:
👉 http://localhost:8080

🔸 Frontend Setup (HTML, CSS, JS)
Open the frontend folder in VS Code

Right-click on index.html → Open with Live Server

The app will open on 👉 http://localhost:5500

Make sure CORS in the backend allows your frontend origin:

java
Copy code
.allowedOrigins("http://localhost:5500")
🧮 Machine Learning Logic
The scoring logic uses a hybrid keyword-based NLP approach:

Extracts candidate skills using pattern matching and tokenization.

Compares extracted keywords with role-specific skill sets.

Calculates a relevance score using the skill overlap ratio.

Highlights missing and extra skills for better recommendations.
