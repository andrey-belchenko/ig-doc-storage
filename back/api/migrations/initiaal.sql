CREATE SCHEMA attachments;

drop table if exists attachments.signature;
drop table if exists attachments.attachment;

CREATE TABLE attachments.attachment (
    id SERIAL PRIMARY KEY,
    filename VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



CREATE TABLE attachments.signature (
    id SERIAL PRIMARY KEY,
    signer_name VARCHAR(255) NOT NULL,
    signed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    attachment_id INTEGER NOT NULL REFERENCES attachments.attachment(id)
);