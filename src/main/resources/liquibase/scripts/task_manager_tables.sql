-- liquibase formatted sql

-- changeset grigorii:create-users-table
CREATE TABLE users (
    id                  bigserial PRIMARY KEY,
    first_name          text,
    last_name           text,
    email               text,
    password            text,
    role                text,
    task_id             bigint
);

-- changeset grigorii:create-tasks-table
CREATE TABLE tasks (
    id                  bigserial PRIMARY KEY,
    title               text,
    description         text,
    status              text,
    priority            text,
    user_id             bigint
);

-- changeset grigorii:create-comments-table
CREATE TABLE comments (
    id                  bigserial PRIMARY KEY,
    text                text,
    user_id             bigint,
    task_id             bigint
);