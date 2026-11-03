ALTER TABLE order_item
    DROP CONSTRAINT fk_orderitem_on_order;

CREATE SEQUENCE IF NOT EXISTS orders_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE orders
(
    id             BIGINT       NOT NULL,
    created_at     TIMESTAMP(6) WITHOUT TIME ZONE,
    updated_at     TIMESTAMP(6) WITHOUT TIME ZONE,
    authority_code VARCHAR(255),
    user_id        BIGINT       NOT NULL,
    status         VARCHAR(255) NOT NULL,
    order_number   VARCHAR(255) NOT NULL,
    total_price    DECIMAL      NOT NULL,
    province       VARCHAR(100) NOT NULL,
    city           VARCHAR(100) NOT NULL,
    postal_code    VARCHAR(10)  NOT NULL,
    phone_number   VARCHAR(11)  NOT NULL,
    static_number  VARCHAR(11)  NOT NULL,
    description    VARCHAR(500),
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

ALTER TABLE orders
    ADD CONSTRAINT uc_orders_ordernumber UNIQUE (order_number);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDERITEM_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

DROP TABLE "order" CASCADE;

DROP SEQUENCE order_seq CASCADE;

ALTER TABLE carts
    ALTER COLUMN updated_at DROP NOT NULL;