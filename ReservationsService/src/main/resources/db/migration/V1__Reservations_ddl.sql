create type status as enum (
    'HELD',
    'CREATED',
    'CONFIRMED',
    'CHECKED_IN',
    'CHECKED_OUT',
    'CANCELLED'
    );

create table if not exists rs_reservation
(
    id                    bigint generated always as identity primary key,
    room_id               bigint       not null,
    booker_user_id        bigint,
    customer_user_id      bigint,
    customer_first_name   varchar(50)  not null,
    customer_last_name    varchar(50)  not null,
    customer_phone_number varchar(20)  not null,
    customer_email        varchar(255) not null,
    check_in_date         date         not null,
    check_out_date        date         not null,
    status                status       not null,
    created_at            timestamp(0) not null default current_timestamp,
    created_by            varchar(120) not null,
    updated_at            timestamp(0),
    updated_by            varchar(120)
);

create table if not exists rs_reservation_history
(
    id             bigint generated always as identity primary key,
    reservation_id bigint       not null references rs_reservation (id),
    old_status     status       not null,
    new_status     status       not null,
    created_at     timestamp(0) not null default current_timestamp,
    created_by     varchar(120) not null
);
