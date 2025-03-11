DROP SCHEMA IF EXISTS attachments CASCADE;
CREATE SCHEMA attachments;

CREATE TABLE attachments.file (
    file_id TEXT PRIMARY KEY,
    file_name TEXT,
    file_size BIGINT
);

CREATE TABLE attachments.attachment (
    attachment_id TEXT PRIMARY KEY,
    object_id TEXT,
    region_id TEXT,
    created_at TIMESTAMPTZ DEFAULT now(),
    deleted_at TIMESTAMPTZ,
    created_by TEXT,
    deleted_by TEXT,
    file_id TEXT REFERENCES attachments.file(file_id)
);

CREATE TABLE attachments.signature (
    signature_id TEXT PRIMARY KEY,
    attachment_id TEXT NOT NULL REFERENCES attachments.attachment(attachment_id),
    created_at TIMESTAMPTZ DEFAULT now(),
    deleted_at TIMESTAMPTZ,
    created_by TEXT,
    deleted_by TEXT,
    file_id TEXT NOT NULL REFERENCES attachments.file(file_id)
);

CREATE TYPE attachments.access_level AS ENUM (
    'READ',
    'WRITE'
);

CREATE TABLE attachments.permission (
    permission_id SERIAL PRIMARY KEY,
    user_id TEXT NOT NULL,
    region_id TEXT,
    access_level attachments.access_level NOT NULL
);