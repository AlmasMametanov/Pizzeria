# Pizzeria
Multilingual project written using Java Servlets. Where functions for ordering food are available, such as adding ingredients to pizza, changing your basket, ordering them, etc.
## Guest:
* registration
* login
* switch language
* view menu
## User:
* add product into basket
* add ingredients to pizza
* edit profile data
* view own basket
* delete and update count of product in basket
* make order
* view all own order list
## Admin:
* create, delete categories
* create, delete, update products and ingredients
* change ingredients of pizza
* change order status
* ban/unban user
* view data of all user
## Technologies
* JavaEE: Servlet, JSP, JSTL
* JDBC
* Bootstrap 5
* Log4j2
* Java 8
* MySQL
* Tomcat 9
* Maven
## Deployment instructions
1. After launching IntelliJ Idea, click 'Get from Version Control', 
select 'Git', then paste the HTTPS address copied from the project on Github into the URL. 
(here is my address: https://github.com/AlmasMametanov/Pizzeria.git). 
Click 'clone'.
2. Run MySQL 8.0 Command Line Client, copy/paste the scripts from /db/db.sql 
and execute the code.
3. Create .env file with 'db.password=' field in the src/main/resources folder.
4. If you created a database from point 2 under a different name,
   then in the ConnectionPool.properties file in the src/main/resources folder,
   you need to correct the 'db.url' under the new name.
5. Connect the Apache Tomcat server to start the project. 
Click on the top 'Run' -> 'Edit Configurations'. Next, in the window that opens, in the upper left corner, 
click '+', select 'Tomcat Server' -> 'Local'. In the window that appears in the 'Application Server', 
select 'Tomcat 9.0.63', then specify 'JRE 1.8'. Next select 'Deployment', press '+', press 'Artifact', 
and select 'Pizzeria:war exploded'. Delete the 'Application context' line. Then click 'Apply' and 'OK'.
6. Now we connect the database. To do this, in the right part of the window, click 'Database', 
then '+' -> 'Data Source' -> 'MySQL'. In the window that appears, 'Database' = 'pizzeria', fill in 'User' and 'Password'. 
Next, click 'Test Connection'. Click 'OK'.
7. You can Run the project.
