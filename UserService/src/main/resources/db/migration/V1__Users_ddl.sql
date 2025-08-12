create table if not exists ru_department
    (
        id         bigint generated always as identity primary key,
        name       varchar(100) not null unique,
        status     varchar(25)  not null default 'ACTIVE',
        manager_id bigint       not null references ru_employee (id),
        created_at timestamp(0) not null default current_timestamp,
        created_by varchar(120) not null,
        updated_at timestamp(0),
        updated_by varchar(120)
    );

create table if not exists ru_employee
    (
        id              bigint generated always as identity primary key,
        first_name      varchar(50)  not null,
        last_name       varchar(50)  not null,
        identity_number varchar(15)  not null unique,
        email           varchar(255) unique,
        phone_number    varchar(13)  not null unique,
        address         varchar(255) not null,
        birth_date      date         not null,
        gender          varchar(25)  not null check (gender in ('MALE', 'FEMALE', 'OTHER')),
        nationality     varchar(100),
        created_at      timestamp(0) not null default current_timestamp,
        created_by      varchar(120) not null,
        updated_at      timestamp(0),
        updated_by      varchar(120)
    );

create table if not exists ru_employee_old
    (
        id              bigint generated always as identity primary key,
        first_name      varchar(50)  not null,
        last_name       varchar(50)  not null,
        identity_number varchar(15)  not null unique,
        email           varchar(255) unique,
        phone_number    varchar(13)  not null unique,
        address         varchar(255) not null,
        birth_date      date         not null,
        gender          varchar(25)  not null check (gender in ('MALE', 'FEMALE', 'OTHER')),
        nationality     varchar(100),
        created_at      timestamp(0) not null default current_timestamp,
        created_by      varchar(120) not null,
        updated_at      timestamp(0),
        updated_by      varchar(120)
    );

create table if not exists ru_position
    (
        id            bigint generated always as identity primary key,
        name          varchar(100) not null unique,
        department_id bigint       not null references ru_department (id),
        status        varchar(25)  not null check (status in ('ACTIVE', 'DELETED')),
        created_at    timestamp(0) not null default current_timestamp,
        created_by    varchar(120) not null,
        updated_at    timestamp(0),
        updated_by    varchar(120)
    );

create table if not exists ru_employee_experience
    (
        id            bigint generated always as identity primary key,
        salary        numeric(12, 2) not null,
        employee_id   bigint         not null references ru_employee (id),
        position_id   bigint         not null references ru_position (id),
        start_date    date           not null,
        end_date      date,
        created_at    timestamp(0)   not null default current_timestamp,
        created_by    varchar(120)   not null,
        updated_at    timestamp(0),
        updated_by    varchar(120)
    );

create table ru_employee_old_experience
    (
        id              bigint generated always as identity primary key,
        salary          numeric(12, 2) not null,
        employee_old_id bigint         not null references ru_employee_old (id),
        position_id     bigint         not null references ru_position (id),
        start_date      date           not null,
        end_date        date           not null,
        created_at      timestamp(0)   not null default current_timestamp,
        created_by      varchar(120)   not null,
        updated_at      timestamp(0),
        updated_by      varchar(120)
    );

