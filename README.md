Hibbernate project

● The main task is to create all the necessary entity classes and map them to the tables in the movie schema.
For the task, 16 entities were created using Hibernate:

actor
address
category
city
country
customer
film
film_actor
film_category
film_text
inventory
language
payment
rental
staff
store

● Add a method that can create a new customer (customer table) with all dependent fields. Make sure the method is transactional to avoid a situation where the customer's address is saved to the database, but the customer itself is not.

● Add a transactional method that describes the event "the customer returned a previously rented movie." Choose any customer and rental event at your discretion. There's no need to recalculate the movie rating.

● Add a transactional method that describes the event "the customer visited the store and rented inventory there." During this process, the customer made a payment to the staff. Choose any movie (via inventory) at your discretion. The only restriction is that the movie must be available for rent. In other words, either there should be no records of the inventory in the rental table, or the return_date column in the rental table must be filled for the last rental of this inventory.

● Add a transactional method that describes the event "a new movie was released and became available for rent." Choose the movie, language, actors, categories, etc., at your discretion.

