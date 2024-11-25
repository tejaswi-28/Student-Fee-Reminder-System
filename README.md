Student Fee Reminder System

This project is a Student Fee Reminder System built with Angular and Spring Boot. It automates the process of sending reminders to students about their upcoming fee due dates via email. 
This system streamlines administrative tasks and ensures timely notifications, benefiting both institutions and students.  

---  

## Features 
1. **Student Data Management**  
   - Import student data (name, email, courses, and fee due dates) from an Excel sheet.  
   - Support for managing up to 4 courses per student.  

2. **Automated Reminders**  
   - Sends email reminders **two days before the due date**.  
   - Provides students with detailed information about the amount due and the deadline.  

3. **User-Friendly Dashboard**  
   - Admin can view, add, or update student data.  
   - Real-time updates of fee status.  

4. **Secure and Reliable**  
   - Ensures data integrity with validations and error handling.  
   - Built-in email configuration using Spring Boot Mail for seamless delivery.  

---  

## Tech Stack  

### **Frontend:**  
- **Angular**  
  - Dynamic UI with seamless navigation and responsive design.  
  - Data binding and real-time updates for a smooth user experience.  

### **Backend:**  
- **Spring Boot**  
  - RESTful APIs for seamless integration with the frontend.  
  - Spring Data JPA for database operations.  
  - Spring Boot Mail for email functionality.  

### **Database:**  
- **MySQL**  
  - Stores student data, course details, and fee due dates.  

---  

## Prerequisites 
### **For Backend:**  
- Java 8 or higher  
- Maven  
- MySQL  

### **For Frontend:**  
- Node.js  
- Angular CLI  

---  

## **Setup Instructions**  

### **Backend Setup (Spring Boot):**  
1. Clone the repository.  
2. Import the project into your IDE (e.g., Spring Tool Suite or IntelliJ IDEA).  
3. Configure `application.properties` with your database credentials:  
   ```properties  
   spring.datasource.url=jdbc:mysql://localhost:3306/student_fee_system  
   spring.datasource.username=your_username  
   spring.datasource.password=your_password  
   spring.mail.host=smtp.gmail.com  
   spring.mail.port=587  
   spring.mail.username=your_email  
   spring.mail.password=your_email_password  
   ```  
4. Run the project as a Spring Boot application.  

### **Frontend Setup (Angular):**  
1. Navigate to the `frontend` directory.  
2. Install dependencies:  
   ```bash  
   npm install  
   ```  
3. Start the development server:  
   ```bash  
   ng serve  
   ```  
4. Access the application at `http://localhost:4200/`.  

---  

## **How It Works**  
1. **Data Upload**: Admin uploads an Excel sheet containing student data.  
2. **Processing**: Backend processes the Excel data and stores it in the database.  
3. **Reminder Logic**: The system checks for students with due dates approaching within 2 days.  
4. **Email Notifications**: Reminder emails are sent to these students automatically.  

---  

## **Future Enhancements**  
- Support for SMS notifications.  
- Multi-language email templates.  
- Advanced analytics for fee tracking and reporting.  
- Integration with payment gateways for online fee submission.  

---  

## **Contributing**  
Contributions are welcome! Please fork the repository and submit a pull request with your proposed changes.  

---  

## **Contact**  
If you have any questions or feedback, feel free to reach out.  

**Author:** Tejaswi Gaikwad  
**LinkedIn:** [Tejaswi Gaikwad](https://www.linkedin.com/in/tejaswi-gaikwad-600556247/)  
