ALTER TABLE CUSTOMER
ADD CREATED_AT TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD UPDATED_AT TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP;