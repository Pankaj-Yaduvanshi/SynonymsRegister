# SynonymsRegister

**Installation**

1. Clone or download the repository using the following link:
2.  ```
      https://github.com/Pankaj-Yaduvanshi/SynonymsRegister.git
     ```
3.  Update the Database and Server Configuration in the application.properties file. Open the src/main/resources/application.properties file and modify the following settings to match your environment:
4.   ```
      server.port=8082
      spring.datasource.url=jdbc:mysql://localhost:3306/synonym-register
      spring.datasource.username=root
      spring.datasource.password=Pnkj@123
     ```
5.   Ensure that the database URL, username, and password are set according to your database configuration.
6.   Test the APIs using Postman or any other API testing tool.

**Usage**
1. Check the Word Entity Class: You can find the Word entity class in the project. This class represents a word and its synonyms. You can modify it or use it as a reference for your data model.
   
2. Explore the Controller Class: The project includes a controller class for handling API requests related to synonyms. You can check this class to understand how the APIs are defined and how they interact with the database.


**Note:**
1. OAuth2 Authentication configuration is under Progress, hence currently commented out. The AppSecurityConfig class is not yet fully configured. 

     

