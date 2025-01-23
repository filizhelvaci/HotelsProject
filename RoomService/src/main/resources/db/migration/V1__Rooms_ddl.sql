create table if not exists rs_room_type
(
    id           bigint generated always as identity primary key,
    name         varchar(100)   not null unique,
    price numeric(14, 2) not null,
    person_count smallint       not null,
    size         smallint       not null,
    description  varchar(1000)  not null,
    created_at   timestamp(0)   not null default current_timestamp,
    created_by   varchar(120)   not null,
    updated_at   timestamp(0),
    updated_by   varchar(120)
);

create table if not exists rs_room
(
    id           bigint generated always as identity primary key,
    number smallint not null unique,
    floor        smallint     not null,
    status       varchar(25)  not null check (status in ('EMPTY', 'FULL', 'RESERVE', 'IN_MAINTENANCE')),
    room_type_id bigint       not null references rs_room_type (id),
    created_at   timestamp(0) not null default current_timestamp,
    created_by   varchar(120) not null,
    updated_at   timestamp(0),
    updated_by   varchar(120)
);

create table if not exists rs_asset
(
    id         bigint generated always as identity primary key,
    name  varchar(50)    not null unique,
    price numeric(14, 2) not null,
    is_default boolean        not null,
    created_at timestamp(0)   not null default current_timestamp,
    created_by varchar(120)   not null,
    updated_at timestamp(0),
    updated_by varchar(120)
);

create table if not exists rs_room_type_asset
(
    id           bigint generated always as identity primary key,
    asset_id     bigint not null references rs_asset (id),
    room_type_id bigint not null references rs_room_type (id)
);
