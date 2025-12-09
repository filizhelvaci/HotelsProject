create type status as enum (
    'CREATED',
    'CONFIRMED',
    'CHECK_IN',
    'CHECK_OUT',
    'CANCELLED'
    );

create type room_reservation_status as enum (
    'RESERVED',
    'CHECK_IN',
    'CHECK_OUT',
    'CANCELLED'
    );

create type currency as enum (
    'TRY'
    );

create type payment_timing as enum (
    'PAY_LATER'
    );

create table if not exists rss_reservation_owner
(
    id           bigint generated always as identity primary key,
    first_name   varchar(50)  not null,
    last_name    varchar(50)  not null,
    email        varchar(255) not null,
    phone_number varchar(13)  not null,
    country      varchar(50)  not null,
    city         varchar(50)  not null,
    address      varchar(255) not null,
    created_at   timestamp(0) not null default current_timestamp,
    created_by   varchar(120) not null,
    updated_at   timestamp(0),
    updated_by   varchar(120)
);

create table if not exists rss_reservation
(
    id             bigint generated always as identity primary key,
    owner_id       bigint         not null references rss_reservation_owner (id),
    code           varchar(6)     not null,
    check_in_date  DATE           not null,
    check_out_date DATE           not null,
    payment_timing payment_timing not null,
    currency       currency       not null,
    total_price    numeric(14, 2) not null,
    status         status         not null,
    created_at     timestamp(0)   not null default current_timestamp,
    created_by     varchar(120)   not null,
    updated_at     timestamp(0),
    updated_by     varchar(120)
);

create table if not exists rss_reserved_room
(
    id               bigint generated always as identity primary key,
    reservation_id   bigint                  not null references rss_reservation (id),
    room_id          bigint                  not null,
    room_price       numeric(14, 2)          not null,
    adult_count      smallint                not null,
    baby_count       smallint                not null,
    kids_count       smallint                not null,
    big_kids_count   smallint                not null,
    guest_first_name varchar(50)             not null,
    guest_last_name  varchar(50)             not null,
    status           room_reservation_status not null,
    created_at       timestamp(0)            not null default current_timestamp,
    created_by       varchar(120)            not null,
    updated_at       timestamp(0),
    updated_by       varchar(120)
);

create table if not exists rss_reservation_history
(
    id              bigint generated always as identity primary key,
    reservation_id  bigint       not null references rss_reservation (id),
    previous_status status       not null,
    new_status      status       not null,
    description     varchar(255),
    created_at      timestamp(0) not null default current_timestamp,
    created_by      varchar(120) not null,
    updated_at      timestamp(0),
    updated_by      varchar(120)
);