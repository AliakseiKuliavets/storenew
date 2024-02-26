-- Insert test data into user table
INSERT INTO "user" ("user_id",
                    "user_nick_name",
                    "user_password",
                    "user_first_name",
                    "user_last_name",
                    "user_email",
                    "user_phone_number",
                    "user_verified_account",
                    "user_role")
VALUES ('a197d1bb-8990-4b08-ad8a-9ec55718fcb8',
        'admin',
        '$2a$10$mcqztegcxHUZ5bT5igeQtuy8WAB99YKvb1t5/cSut16zs0wsCK8n6',
        'Aliaksei',
        'Kuliavets',
        'abc@gmail.com',
        '+487576152478',
        1,
        'ADMIN');

-- Insert test data into user2 table
INSERT INTO "user" ("user_id",
                    "user_nick_name",
                    "user_password",
                    "user_first_name",
                    "user_last_name",
                    "user_email",
                    "user_phone_number",
                    "user_verified_account",
                    "user_role")
VALUES ('0480101b-0fa4-4b13-939e-062a7a8c49e6',
        'Bogdan',
        'boss',
        '12345678',
        'Podalevic',
        'bogomdan@gmail.com',
        '+37176152478',
        1,
        'USER');

-- Insert test data into reviewed table
INSERT INTO "reviewed" ("reviewed_id", "user_reviewed", "user_received_review", "review_rating")
VALUES ('c740ed50-b68b-4f7b-91a0-ac81fc34f457',
        '0480101b-0fa4-4b13-939e-062a7a8c49e6',
        'a197d1bb-8990-4b08-ad8a-9ec55718fcb8',
        8.0);

-- Insert test data into product_1 table
INSERT INTO "product" ("product_id",
                       "product_name",
                       "product_price",
                       "product_description",
                       "product_category",
                       "product_brand",
                       "placed_by_user",
                       "purchased_by_user",
                       "date_of_create")
VALUES ('35026fc0-dbfc-4d52-9c1c-a203929ea63d',
        'Iphone 8',
        1800.00,
        'Simple description',
        'ELECTRONICS',
        'APPLE',
        'a197d1bb-8990-4b08-ad8a-9ec55718fcb8',
        '0480101b-0fa4-4b13-939e-062a7a8c49e6',
        '2024-05-09');

-- Insert test data into payment table
INSERT INTO "payment" ("payment_id", "payment_method", "payment_status_tracking")
VALUES ('3b64c7e1-dbf4-4611-a679-e29852659de0', 'CASH', 'PENDING');

-- Insert test data into order_number table
INSERT INTO "order_number" ("order_number_id",
                            "order_number_date",
                            "product_id",
                            "delivery_id",
                            "order_status",
                            "payment_id",
                            "recipient_user_id",
                            "sender_user_id")
VALUES ('770d42f7-4765-4fab-88aa-86dad34995eb',
        '2024-01-11',
        '35026fc0-dbfc-4d52-9c1c-a203929ea63d',
        '1fac8484-5093-4532-b6d8-d632257c84cc',
        'NEW',
        '3b64c7e1-dbf4-4611-a679-e29852659de0',
        'a197d1bb-8990-4b08-ad8a-9ec55718fcb8',
        '0480101b-0fa4-4b13-939e-062a7a8c49e6');

-- Insert test data into delivery table
INSERT INTO "delivery" ("delivery_id", "delivery_address", "payment_method", "delivery_status_tracking")
VALUES ('1fac8484-5093-4532-b6d8-d632257c84cc', 'Berlin, ul 17 Juli, 23', 'CASH', 'NEW');