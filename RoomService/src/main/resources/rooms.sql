create table rs_room_type (
                              id bigint generated always as identity primary key,
                              name varchar(50) not null unique,
                              price numeric(14,4) not null,
                              person_count smallint not null,
                              size numeric(4,2) not null,
                              description varchar(200)
);
create table rs_room      (
                           id bigint generated always as identity primary key,
                           type_id bigint not null references rs_room_type(id),
                           number smallint not null ,
                           floor smallint not null,
                           status varchar(25) not null check (status in ('empty', 'full', 'reserve', 'in_maintenance'))
                          );



create table rs_asset     (
                           id bigint generated always as identity primary key,
                           name varchar(50) not null,
                           price numeric(14,4) not null,
                           is_default smallint not null
                          );

create table rs_room_type_asset (
                           id bigint generated always as identity primary key,
                           room_type_id bigint not null references  rs_room_type(id),
                           asset_id bigint not null references rs_asset(id)
                          );
