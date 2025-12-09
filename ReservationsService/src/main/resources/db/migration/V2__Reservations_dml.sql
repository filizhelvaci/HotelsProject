-- ----------------------------------------------------------------------
-- DML for Reservation Owner Data
-- ----------------------------------------------------------------------

insert into rss_reservation_owner (first_name, last_name, email, phone_number, country, city, address, created_by)
values ('fatma', 'ozturk', 'fatma.ozturk@gmail.com', '5321234567',
        'turkey', 'istanbul', 'kadikoy mah. 12', 'system');

insert into rss_reservation_owner (first_name, last_name, email, phone_number, country, city, address, created_by)
values ('ali', 'yildirim', 'ali.yildirim@hotmail.com', '5332345678',
        'turkey', 'ankara', 'cankaya 45', 'system');

insert into rss_reservation_owner (first_name, last_name, email, phone_number, country, city, address, created_by)
values ('zeynep', 'kara', 'zeynep.kara@gmail.com', '5343456789',
        'turkey', 'izmir', 'konak 200', 'system');

insert into rss_reservation_owner (first_name, last_name, email, phone_number, country, city, address, created_by)
values ('hasan', 'demir', 'hasan.demir@yahoo.com', '5354567890',
        'turkey', 'antalya', 'mur. pasa 33', 'system');

insert into rss_reservation_owner (first_name, last_name, email, phone_number, country, city, address, created_by)
values ('emine', 'celik', 'emine.celik@gmail.com', '5365678901',
        'turkey', 'bursa', 'niluf. sk. 67', 'system');

insert into rss_reservation_owner (first_name, last_name, email, phone_number, country, city, address, created_by)
values ('ahmet', 'tas', 'ahmet.tas@hotmail.com', '5376789012',
        'turkey', 'kayseri', 'melikgazi 9', 'system');

insert into rss_reservation_owner (first_name, last_name, email, phone_number, country, city, address, created_by)
values ('melis', 'aksoy', 'melis.aksoy@gmail.com', '5387890123',
        'turkey', 'antalya', 'lara 55', 'system');

insert into rss_reservation_owner (first_name, last_name, email, phone_number, country, city, address, created_by)
values ('mehmet', 'arslan', 'mehmet.arslan@gmail.com', '5398901234',
        'turkey', 'ankara', 'etimesgut 3', 'system');

insert into rss_reservation_owner (first_name, last_name, email, phone_number, country, city, address, created_by)
values ('elif', 'kurt', 'elif.kurt@gmail.com', '5309012345',
        'turkey', 'istanbul', 'uskudar 11', 'system');

insert into rss_reservation_owner (first_name, last_name, email, phone_number, country, city, address, created_by)
values ('ayse', 'bingol', 'ayse.bingol@gmail.com', '5310123456',
        'turkey', 'izmir', 'bornova 7', 'system');

-- ----------------------------------------------------------------------
-- DML for Reservation Data
-- ----------------------------------------------------------------------

insert into rss_reservation (owner_id, code, check_in_date, check_out_date, payment_timing, currency,
                             total_price, status, created_by)
values (1, 'A10001', '2025-10-01', '2025-10-05', 'PAY_LATER',
        'TRY', 4500.00, 'CREATED', 'admin');

insert into rss_reservation (owner_id, code, check_in_date, check_out_date, payment_timing, currency,
                             total_price, status, created_by)
values (2, 'A10002', '2025-10-02', '2025-10-06', 'PAY_LATER',
        'TRY', 5200.00, 'CONFIRMED', 'admin');

insert into rss_reservation (owner_id, code, check_in_date, check_out_date, payment_timing, currency,
                             total_price, status, created_by)
values (3, 'A10003', '2025-10-03', '2025-10-04', 'PAY_LATER',
        'TRY', 1600.00, 'CHECK_IN', 'system');

insert into rss_reservation (owner_id, code, check_in_date, check_out_date, payment_timing, currency,
                             total_price, status, created_by)
values (4, 'A10004', '2025-10-05', '2025-10-08', 'PAY_LATER',
        'TRY', 6000.00, 'CHECK_OUT', 'system');

insert into rss_reservation (owner_id, code, check_in_date, check_out_date, payment_timing, currency,
                             total_price, status, created_by)
values (5, 'A10005', '2025-10-06', '2025-10-10', 'PAY_LATER',
        'TRY', 6800.00, 'CANCELLED', 'admin');

insert into rss_reservation (owner_id, code, check_in_date, check_out_date, payment_timing, currency,
                             total_price, status, created_by)
values (6, 'A10006', '2025-11-01', '2025-11-05', 'PAY_LATER',
        'TRY', 4200.00, 'CREATED', 'admin');

insert into rss_reservation (owner_id, code, check_in_date, check_out_date, payment_timing, currency,
                             total_price, status, created_by)
values (7, 'A10007', '2025-11-02', '2025-11-06', 'PAY_LATER',
        'TRY', 5300.00, 'CONFIRMED', 'system');

insert into rss_reservation (owner_id, code, check_in_date, check_out_date, payment_timing, currency,
                             total_price, status, created_by)
values (8, 'A10008', '2025-11-03', '2025-11-04', 'PAY_LATER',
        'TRY', 2000.00, 'CHECK_IN', 'admin');

insert into rss_reservation (owner_id, code, check_in_date, check_out_date, payment_timing, currency,
                             total_price, status, created_by)
values (9, 'A10009', '2025-11-05', '2025-11-09', 'PAY_LATER',
        'TRY', 7000.00, 'CHECK_OUT', 'system');

insert into rss_reservation (owner_id, code, check_in_date, check_out_date, payment_timing, currency,
                             total_price, status, created_by)
values (10, 'A10010', '2025-11-06', '2025-11-10', 'PAY_LATER',
        'TRY', 7500.00, 'CANCELLED', 'admin');

-- ----------------------------------------------------------------------
-- DML for Reserved Room Data
-- ----------------------------------------------------------------------

insert into rss_reserved_room (reservation_id, room_id, room_price, adult_count, baby_count, kids_count,
                               big_kids_count, guest_first_name, guest_last_name, status, created_by)
values (1, 101, 4500.00, 2, 0, 1, 0,
        'fatma', 'ozturk', 'RESERVED', 'admin');

insert into rss_reserved_room (reservation_id, room_id, room_price, adult_count, baby_count, kids_count,
                               big_kids_count, guest_first_name, guest_last_name, status, created_by)
values (2, 102, 5200.00, 2, 1, 0, 0,
        'ali', 'yildirim', 'RESERVED', 'admin');

insert into rss_reserved_room (reservation_id, room_id, room_price, adult_count, baby_count, kids_count,
                               big_kids_count, guest_first_name, guest_last_name, status, created_by)
values (3, 103, 1600.00, 1, 0, 0, 0,
        'zeynep', 'kara', 'CHECK_IN', 'system');

insert into rss_reserved_room (reservation_id, room_id, room_price, adult_count, baby_count, kids_count,
                               big_kids_count, guest_first_name, guest_last_name, status, created_by)
values (4, 104, 6000.00, 2, 0, 2, 0,
        'hasan', 'demir', 'CHECK_OUT', 'system');

insert into rss_reserved_room (reservation_id, room_id, room_price, adult_count, baby_count, kids_count,
                               big_kids_count, guest_first_name, guest_last_name, status, created_by)
values (5, 105, 6800.00, 2, 0, 1, 1,
        'emine', 'celik', 'CANCELLED', 'admin');

insert into rss_reserved_room (reservation_id, room_id, room_price, adult_count, baby_count, kids_count,
                               big_kids_count, guest_first_name, guest_last_name, status, created_by)
values (6, 106, 4200.00, 1, 0, 1, 0,
        'ahmet', 'tas', 'RESERVED', 'admin');

insert into rss_reserved_room (reservation_id, room_id, room_price, adult_count, baby_count, kids_count,
                               big_kids_count, guest_first_name, guest_last_name, status, created_by)
values (7, 107, 5300.00, 2, 1, 0, 0,
        'melis', 'aksoy', 'CONFIRMED', 'system');

insert into rss_reserved_room (reservation_id, room_id, room_price, adult_count, baby_count, kids_count,
                               big_kids_count, guest_first_name, guest_last_name, status, created_by)
values (8, 108, 2000.00, 1, 0, 0, 0,
        'mehmet', 'arslan', 'CHECK_IN', 'admin');

insert into rss_reserved_room (reservation_id, room_id, room_price, adult_count, baby_count, kids_count,
                               big_kids_count, guest_first_name, guest_last_name, status, created_by)
values (9, 109, 7000.00, 2, 0, 1, 0,
        'elif', 'kurt', 'CHECK_OUT', 'system');

insert into rss_reserved_room (reservation_id, room_id, room_price, adult_count, baby_count, kids_count,
                               big_kids_count, guest_first_name, guest_last_name, status, created_by)
values (10, 110, 7500.00, 2, 0, 2, 0,
        'ayse', 'bingol', 'CANCELLED', 'admin');

-- ----------------------------------------------------------------------
-- DML for Reservation History Data
-- ----------------------------------------------------------------------

insert into rss_reservation_history (reservation_id, previous_status, new_status, description, created_by)
values (1, 'CREATED', 'CONFIRMED', 'owner confirmed reservation',
        'admin');

insert into rss_reservation_history (reservation_id, previous_status, new_status, description, created_by)
values (2, 'CONFIRMED', 'CHECK_IN', 'guest checked in',
        'admin');

insert into rss_reservation_history (reservation_id, previous_status, new_status, description, created_by)
values (3, 'CHECK_IN', 'CHECK_OUT', 'guest left room',
        'system');

insert into rss_reservation_history (reservation_id, previous_status, new_status, description, created_by)
values (4, 'CREATED', 'CANCELLED', 'cancel after stay',
        'system');

insert into rss_reservation_history (reservation_id, previous_status, new_status, description, created_by)
values (5, 'CREATED', 'CANCELLED', 'customer cancelled',
        'admin');

insert into rss_reservation_history (reservation_id, previous_status, new_status, description, created_by)
values (6, 'CREATED', 'CONFIRMED', 'reservation approved',
        'admin');

insert into rss_reservation_history (reservation_id, previous_status, new_status, description, created_by)
values (7, 'CONFIRMED', 'CHECK_IN', 'guest arrived',
        'system');

insert into rss_reservation_history (reservation_id, previous_status, new_status, description, created_by)
values (8, 'CHECK_IN', 'CHECK_OUT', 'short stay completed',
        'admin');

insert into rss_reservation_history (reservation_id, previous_status, new_status, description, created_by)
values (9, 'CONFIRMED', 'CANCELLED', 'post-completion cancel',
        'system');

insert into rss_reservation_history (reservation_id, previous_status, new_status, description, created_by)
values (10, 'CREATED', 'CANCELLED', 'cancelled by owner',
        'admin');
