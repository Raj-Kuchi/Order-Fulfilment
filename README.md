# Order-Fulfilment
The project is implemented in Java on a Spring Boot application using the H2 database. 
Included the Postman collection used to test the REST APIs.

Following APIs are implemented:<br>
/init_catalog<br>
/get_catalog<br>
/process_restock<br>
/process_order (The ship_package logic is implemented as a continuation of the processing of orders. It Prints out the shipping orders in the required format. This logic can be called from other places or independently as separate API) <br><br>


TODO: Add test cases 
