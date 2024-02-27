# SecondTreasure Project [Backend]

## Project Goal

The aim of the "SecondTreasure" project is to provide a convenient platform where users can buy and sell second-hand
goods across a diverse range. This online marketplace facilitates the second life of items, promoting their resale and
contributing to the reduction of environmental impact.

### Technologies used:

- Java 17 (latest master commits)
- Gradle
- MySql
- Liquibase
- Spring MVC
- Spring Data JPA and Hibernate
- Spring Security
- Freemarker
- Swagger
- Inegration Test

### How to run the app:

1. Setup MySQL DB (create db "test_database" for test and "storenew" for app
2. Use your username and password to connect to the DB in Application.yml
3. Access URL: http://localhost:8080/api/ for demo view
4. Or test app in Postman
5. Documentation on Swagger http://localhost:8080/swagger-ui/index.html#/

## Entrance on app

- login: admin
- password: admin

## Database structure

This section outlines the database schema for the "SecondTreasure" project, serving as the backend structure for
managing users, product listings, orders, payments, deliveries, reviews, and images.

### Table User (Users of the Platform)

| Column name           | Type         | Description                                                 |
|-----------------------|--------------|-------------------------------------------------------------|
| user_id               | uuid         | Unique identifier for the user - primary key, not null      |
| user_nick_name        | varchar(45)  | Unique nickname, not null                                   |
| user_password         | varchar(255) | Password of user accaunt                                    |
| user_first_name       | varchar(45)  | First name of the user                                      |
| user_last_name        | varchar(45)  | Last name of the user, not null                             |
| user_email            | varchar(45)  | Email address of the user, not null                         |
| user_phone_number     | varchar(45)  | Phone number of the user, not null                          |
| user_verified_account | boolean      | Flag indicating if the user's account is verified, not null |
| user_role             | varchar(45)  | User role                                                   |

### Table Product (Listings of Second-Hand Goods)

| Column name         | Type         | Description                                               |
|---------------------|--------------|-----------------------------------------------------------|
| product_id          | uuid         | Unique identifier for the product - primary key, not null |
| product_name        | varchar(45)  | Name or title of the product, nullable                    |
| product_price       | decimal(8,2) | Selling price of the product, nullable                    |
| product_description | varchar(255) | Detailed description of the product, not null             |
| product_category    | varchar(45)  | Category of the product, not null                         |
| product_brand       | varchar(45)  | Brand of the product, not null                            |
| placed_by_user      | uuid         | User ID who placed the product listing, not null          |
| purchased_by_user   | uuid         | User ID who purchased the product, not null               |
| date_of_create      | Date         | Date of create product                                    |
| preview_image_id    | long         | Image id                                                  |

### Table Order Number (Order Information)

| Column name       | Type        | Description                                                        |
|-------------------|-------------|--------------------------------------------------------------------|
| order_number_id   | uuid        | Unique identifier for the order - primary key, not null            |
| order_number_date | date        | Date of the order, not null                                        |
| product_id        | uuid        | Product ID, foreign key referring to the Product table, not null   |
| delivery_id	      | uuid        | Delivery ID, foreign key referring to the Delivery table, not null |
| order_status      | varchar(45) | Status of the order, nullable                                      |
| payment_id        | uuid        | Payment ID, foreign key referring to the Payment table, not null   |
| recipient_user_id | uuid        | User ID of the recipient, not null                                 |
| sender_user_id    | uuid        | User ID of the sender, not null                                    |

### Table Payment (Payment Information)

| Column name             | Type        | Description                                               |
|-------------------------|-------------|-----------------------------------------------------------|
| payment_id              | uuid        | Unique identifier for the payment - primary key, not null |
| payment_method          | varchar(45) | Payment method used, not null                             |
| payment_status_tracking | varchar(45) | Tracking status of the payment, not null                  |

### Table Delivery (Delivery Information)

| Column name              | Type        | Description                                                |
|--------------------------|-------------|------------------------------------------------------------|
| delivery_id              | uuid        | Unique identifier for the delivery - primary key, not null |
| delivery_address         | varchar(45) | Address for the delivery, not null                         |
| payment_method           | varchar(45) | Payment method for the delivery, not null                  |
| delivery_status_tracking | varchar(45) | Tracking status of the delivery, not null                  |

### Table Reviewed (User Reviews and Ratings)

| Column name          | Type         | Description                                              |
|----------------------|--------------|----------------------------------------------------------|
| reviewed_id          | uuid         | Unique identifier for the review - primary key, not null |
| user_reviewed        | uuid         | 	User ID being reviewed, not null                        |
| user_received_review | uuid         | User ID who received the review, not null                |
| review_rating        | decimal(2,1) | Rating given in the review, nullable                     |

### Table Image (Images for Products)

| Column name              | Type        | Description                                               |
|--------------------------|-------------|-----------------------------------------------------------|
| image_id                 | BIGSERIAL   | Unique identifier for the image - primary key, nullable   |
| image_name               | varchar(45) | Name of the image, nullable                               |
| image_original_file_name | varchar(45) | Original file name of the image, nullable                 |
| image_size	              | long        | Size of the image file, nullable                          |
| image_content_type       | varchar(45) | Content type of the image, nullable                       |
| image_is_preview_image   | boolean     | Flag indicating if the image is a preview image, nullable |
| bytes                    | long        | Storing pictures                                          |
