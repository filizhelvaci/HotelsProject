create table hotel_room.rs_room_type
(
    id           bigint generated always as identity primary key,
    name         varchar(50)    not null unique,
    price        numeric(14, 4) not null,
    person_count smallint       not null,
    size         smallint  not null,
    description  varchar(200),
    created_at timestamp(0) not null default current_timestamp,
    created_by varchar(120) not null ,
    update_at timestamp(0) not null default current_timestamp,
    update_by varchar(120) not null
);
create table hotel_room.rs_room
(
    id      bigint generated always as identity primary key,
    type_id bigint      not null references rs_room_type (id),
    number  smallint    not null,
    floor   smallint    not null,
    status  varchar(25) not null check (status in ('EMPTY', 'FULL', 'RESERVE', 'IN_MAINTENANCE')),
    created_at timestamp(0) not null default current_timestamp,
    created_by varchar(120) not null ,
    update_at timestamp(0) not null default current_timestamp,
    update_by varchar(120) not null
);



create table hotel_room.rs_asset
(
    id         bigint generated always as identity primary key,
    name       varchar(50)    not null,
    price      numeric(14, 4) not null,
    is_default smallint       not null,
    created_at timestamp(0) not null default current_timestamp,
    created_by varchar(120) not null ,
    update_at timestamp(0) not null default current_timestamp,
    update_by varchar(120) not null
);

create table hotel_room.rs_room_type_asset
(
    id           bigint generated always as identity primary key,
    room_type_id bigint not null references rs_room_type (id),
    asset_id     bigint not null references rs_asset (id),
    created_at timestamp(0) not null default current_timestamp,
    created_by varchar(120) not null ,
    update_at timestamp(0) not null default current_timestamp,
    update_by varchar(120) not null
);
