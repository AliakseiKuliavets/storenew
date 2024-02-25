-- ChangeSet: create user table
CREATE TABLE IF NOT EXISTS "user"
(
    "user_id"               CHAR(36) PRIMARY KEY       NOT NULL,
    "user_nick_name"        VARCHAR(45)                NOT NULL UNIQUE,
    "user_password"         VARCHAR(255)               NOT NULL,
    "user_first_name"       VARCHAR(45)                NOT NULL,
    "user_last_name"        VARCHAR(45)                NOT NULL,
    "user_email"            VARCHAR(45)                NOT NULL UNIQUE,
    "user_phone_number"     VARCHAR(45),
    "user_verified_account" BOOLEAN,
    "user_role"             VARCHAR(45) DEFAULT 'USER' NOT NULL
);

-- ChangeSet: create product table
CREATE TABLE IF NOT EXISTS "product"
(
    "product_id"          CHAR(36) PRIMARY KEY NOT NULL,
    "product_name"        VARCHAR(45),
    "product_price"       DECIMAL(8, 2),
    "product_description" VARCHAR(255)         NOT NULL,
    "product_category"    VARCHAR(45)          NOT NULL,
    "product_brand"       VARCHAR(45)          NOT NULL,
    "placed_by_user"      CHAR(36)             NOT NULL,
    "purchased_by_user"   CHAR(36),
    "date_of_create"      DATE                 NOT NULL,
    "preview_image_id"    BIGINT
);

-- ChangeSet: create order_number table
CREATE TABLE IF NOT EXISTS "order_number"
(
    "order_number_id"   CHAR(36) PRIMARY KEY NOT NULL,
    "order_number_date" DATE                 NOT NULL,
    "product_id"        CHAR(36),
    "delivery_id"       CHAR(36)             NOT NULL,
    "order_status"      VARCHAR(45),
    "payment_id"        CHAR(36)             NOT NULL,
    "recipient_user_id" CHAR(36)             NOT NULL,
    "sender_user_id"    CHAR(36)             NOT NULL
);

-- ChangeSet: create payment table
CREATE TABLE IF NOT EXISTS "payment"
(
    "payment_id"              CHAR(36) PRIMARY KEY NOT NULL,
    "payment_method"          VARCHAR(45)          NOT NULL,
    "payment_status_tracking" VARCHAR(45)          NOT NULL
);

-- ChangeSet: create delivery table
CREATE TABLE IF NOT EXISTS "delivery"
(
    "delivery_id"              CHAR(36) PRIMARY KEY NOT NULL,
    "delivery_address"         VARCHAR(45)          NOT NULL,
    "payment_method"           VARCHAR(45)          NOT NULL,
    "delivery_status_tracking" VARCHAR(45)          NOT NULL
);

-- ChangeSet: create reviewed table
CREATE TABLE IF NOT EXISTS "reviewed"
(
    "reviewed_id"          CHAR(36) PRIMARY KEY NOT NULL,
    "user_reviewed"
                           CHAR(36)             NOT NULL UNIQUE,
    "user_received_review" CHAR(36)             NOT NULL,
    "review_rating"        DECIMAL(2, 1)
);

-- ChangeSet: create image table
CREATE TABLE IF NOT EXISTS "image"
(
    "image_id"                 BIGINT PRIMARY KEY NOT NULL,
    "image_name"               VARCHAR(45),
    "image_original_file_name" VARCHAR(45),
    "image_size"               BIGINT,
    "image_content_type"       VARCHAR(45),
    "image_is_preview_image"   BOOLEAN,
    "bytes"                    BIGINT
);
