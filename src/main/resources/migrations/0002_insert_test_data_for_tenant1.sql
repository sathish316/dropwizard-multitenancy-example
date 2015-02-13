--liquibase formatted sql

--changeset exampleservice:1
insert into projects (name, description, tenant_id)
VALUES
('foo', 'foo desc', 'WAYNE'),
('bar', 'bar desc', 'WAYNE');