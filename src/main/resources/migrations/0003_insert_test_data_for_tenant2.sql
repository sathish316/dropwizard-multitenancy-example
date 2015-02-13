--liquibase formatted sql

--changeset exampleservice:1
insert into projects (name, description, tenant_id)
VALUES
('baz', 'baz desc', 'STARK'),
('quu', 'quu desc', 'STARK');