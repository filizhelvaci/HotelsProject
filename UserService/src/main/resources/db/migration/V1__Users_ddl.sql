create table if not exists employee
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

create table if not exists old_employee
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

create table if not exists employee_work_status
(
    id            bigint generated always as identity primary key,
    salary        numeric(12, 2) not null,
    employee_id   bigint         not null references employee (id),
    position_id   bigint         not null references position (id),
    supervisor_id bigint         not null references employee (id),
    start_date    date           not null,
    end_date      date,
    created_at    timestamp(0)   not null default current_timestamp,
    created_by    varchar(120)   not null,
    updated_at    timestamp(0),
    updated_by    varchar(120)
);

create table old_employee_work_status
(
    id            bigint generated always as identity primary key,
    salary        numeric(12, 2) not null,
    employee_id   bigint         not null references old_employee (id),
    position_id   bigint         not null references position (id),
    supervisor_id bigint         not null references old_employee (id),
    start_date    date           not null,
    end_date      date           not null,
    created_at    timestamp(0)   not null default current_timestamp,
    created_by    varchar(120)   not null,
    updated_at    timestamp(0),
    updated_by    varchar(120)
);

create table if not exists department
(
    id         bigint generated always as identity primary key,
    name       varchar(100) not null unique,
    status     varchar(25)  not null check (status in ('ACTIVE', 'DELETED')),
    created_at timestamp(0) not null default current_timestamp,
    created_by varchar(120) not null,
    updated_at timestamp(0),
    updated_by varchar(120)
);

create table if not exists position
(
    id            bigint generated always as identity primary key,
    name          varchar(100) not null unique,
    department_id bigint       not null references department (id),
    status        varchar(25)  not null check (status in ('ACTIVE', 'DELETED')),
    created_at    timestamp(0) not null default current_timestamp,
    created_by    varchar(120) not null,
    updated_at    timestamp(0),
    updated_by    varchar(120)
);
