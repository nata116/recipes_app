# recipes_app
Recipes App by Anastasia Rapti

This is a simple web application, where the user can add recipes, search recipes, login and register.

Used Technologies:
Backend: Java(Corretto-17), Spring Boot, Spring Web, Spring Data JPA, Thymleaf
Frontend: JavaScript (Fetch API), HTML, CSS
Database (Optional): MySQL / PostgreSQL / H2
Build Tool: Maven


GET     /               Opens the login screen
GET     /home           Opens the home screen
GET     /login          The user login in the app
GET     /register       The user registers in the app
POST    /recipes/save   Save the recipe in database
GET     /recipes/search Gets the recipe data 
